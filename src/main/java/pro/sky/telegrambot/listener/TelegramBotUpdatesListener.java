package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Client;
import pro.sky.telegrambot.model.Report;
import pro.sky.telegrambot.repository.ClientRepository;
import pro.sky.telegrambot.repository.ReportRepository;
import pro.sky.telegrambot.service.*;
import pro.sky.telegrambot.markup.KeyboardMarkup;
import pro.sky.telegrambot.markup.ShelterKeyboardMarkup;
import pro.sky.telegrambot.service.Impl.CatShelterServiceImpl;
import pro.sky.telegrambot.service.Impl.DogInfoSelectionServiceImpl;
import pro.sky.telegrambot.service.Impl.DogShelterServiceImpl;

import javax.annotation.PostConstruct;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static pro.sky.telegrambot.constans.Constans.*;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {


    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final String photoAnimalDir;
    private final DogShelterServiceImpl dogShelterMarkup;
    private final AnimalInfoSelectionService animalInfoSelectionService;
    private final KeyboardMarkup keyboardMarkup;
    private final CatShelterServiceImpl catShelterServiceImpl;
    private final CatInfoSelectionService catInfoSelectionService;
    private final DogInfoSelectionServiceImpl dogInfoSelectionService;

    private ReportRepository reportRepository;
    private ClientRepository clientRepository;

    private final ShelterKeyboardMarkup shelterKeyboardMarkup;
    private boolean isWaitNumber = false;
    private boolean photoCheckButton = false;
    private boolean reportCheckButton = false;
    @Autowired
    public TelegramBotUpdatesListener(@Value("${path.to.photo.folder}") String photoAnimalDir,
                                      DogShelterServiceImpl dogShelterService,
                                      AnimalInfoSelectionService animalInfoSelectionService,
                                      KeyboardMarkup keyboardMarkup,
                                      CatShelterServiceImpl catShelterService,
                                      CatInfoSelectionService catInfoSelectionService,
                                      DogInfoSelectionServiceImpl dogInfoSelectionService,
                                      ReportRepository reportRepository, ClientRepository clientRepository, ShelterKeyboardMarkup shelterKeyboardMarkup) {
        this.photoAnimalDir = photoAnimalDir;
        this.dogShelterMarkup = dogShelterService;
        this.animalInfoSelectionService = animalInfoSelectionService;
        this.keyboardMarkup = keyboardMarkup;
        this.catShelterServiceImpl = catShelterService;
        this.catInfoSelectionService = catInfoSelectionService;
        this.dogInfoSelectionService = dogInfoSelectionService;
        this.reportRepository = reportRepository;
        this.clientRepository = clientRepository;
        this.shelterKeyboardMarkup = shelterKeyboardMarkup;
    }

    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            String textMessage = update.message().text();
            Long chat_id = update.message().chat().id();

            if(textMessage!=null){
                if (textMessage.equals("/start")
                        || textMessage.equals(BUTTON_TO_THE_BEGINNING)
                        || textMessage.equals(BUTTON_BEGINNING)) {
                    executeSendMessage(KeyboardMarkup.boardMarkupCatAndDog(chat_id));

                } else if (textMessage.equals(BUTTON_CAT_SHELTER)
                        || textMessage.equals(BUTTON_TO_THE_BEGINNING_CAT)) {
                    executeSendMessage(keyboardMarkup.boardMarkupStageZeroCat(chat_id));

                } else if (textMessage.equals(BUTTON_INFO_CAT_SHELTER)) {
                    executeSendMessage(keyboardMarkup.boardMarkupStageOneCat(chat_id));
                } else if (textMessage.equals(BUTTON_ABOUT_CAT_SHELTER)) {
                    executeSendMessage(catShelterServiceImpl.shelterStory(chat_id));
                } else if (textMessage.equals(BUTTON_MODE_CAT_SHELTER)) {
                    executeSendMessage(catShelterServiceImpl.getWorkScheduleFromDB(chat_id));
                } else if (textMessage.equals(BUTTON_ADDRESS_CAT_SHELTER)) {
                    executeSendMessage(catShelterServiceImpl.getAddressFromDB(chat_id));
                } else if (textMessage.equals(BUTTON_SCHEME_CAT_SHELTER)) {
                    executePhoto(catShelterServiceImpl.getDrivingDirections(chat_id));
                } else if (textMessage.equals(BUTTON_PASS_CAT_SHELTER)) {
                    executeSendMessage(catShelterServiceImpl.getShelterPhoneNumberSecurityFromDB(chat_id));
                } else if (textMessage.equals(BUTTON_SAFETY_CAT_SHELTER)) {
                    executeSendMessage(catShelterServiceImpl.getShelterSafetyPrecautionsSecurityFromDB(chat_id));
                } else if (textMessage.equals(BUTTON_PHONE_CAT_SHELTER)
                        || textMessage.equals(BUTTON_PHONE_DOG_SHELTER)) {
                    executeSendMessage(shelterKeyboardMarkup.contactSelection(chat_id));
                    isWaitNumber = true;
                } else if (textMessage.matches("^\\+\\d{1,} \\d{3} \\d{3} \\d{2} \\d{2}$") && isWaitNumber) {
                    executeSendMessage(shelterKeyboardMarkup.saveClientContact(chat_id, update));
                    isWaitNumber = false;
                } else if (textMessage.equals(BUTTON_VOLUNTEER_CAT_SHELTER)) {
                    executeSendMessage(catShelterServiceImpl.getVolunteersShelter(chat_id));

                } else if (textMessage.equals(BUTTON_DOG_SHELTER)
                        || textMessage.equals(BUTTON_TO_THE_BEGINNING_DOG)) {
                    executeSendMessage(keyboardMarkup.boardMarkupStageZeroDog(chat_id));

                } else if (textMessage.equals(BUTTON_INFO_DOG_SHELTER)) {
                    executeSendMessage(keyboardMarkup.boardMarkupStageOneDog(chat_id));
                } else if (textMessage.equals(BUTTON_ABOUT_DOG_SHELTER)) {
                    executeSendMessage(dogShelterMarkup.shelterStory(chat_id));
                } else if (textMessage.equals(BUTTON_MODE_DOG_SHELTER)) {
                    executeSendMessage(dogShelterMarkup.getWorkScheduleFromDB(chat_id));
                } else if (textMessage.equals(BUTTON_ADDRESS_DOG_SHELTER)) {
                    executeSendMessage(dogShelterMarkup.getAddressFromDB(chat_id));
                } else if (textMessage.equals(BUTTON_SCHEME_DOG_SHELTER)) {
                    executePhoto(dogShelterMarkup.getDrivingDirections(chat_id));
                } else if (textMessage.equals(BUTTON_PASS_DOG_SHELTER)) {
                    executeSendMessage(dogShelterMarkup.getShelterPhoneNumberSecurityFromDB(chat_id));
                } else if (textMessage.equals(BUTTON_SAFETY_DOG_SHELTER)) {
                    executeSendMessage(dogShelterMarkup.getShelterSafetyPrecautionsSecurityFromDB(chat_id));
                } else if (textMessage.equals(BUTTON_VOLUNTEER_DOG_SHELTER)) {
                    executeSendMessage(dogShelterMarkup.getVolunteersShelter(chat_id));


                } else if (textMessage.equals(BUTTON_DATING_RULES)) {
                    executeSendMessage(animalInfoSelectionService.datingRulesSelection(chat_id));
                } else if (textMessage.equals(BUTTON_TRANSPORTATION_RECOMMENDATION)) {
                    executeSendMessage(animalInfoSelectionService.transportationSelection(chat_id));
                } else if (textMessage.equals(BUTTON_LIST_DOCUMENTS)) {
                    executeSendMessage(animalInfoSelectionService.documentsSelection(chat_id));
                } else if (textMessage.equals(BUTTON_REASONS_REFUSAL)) {
                    executeSendMessage(animalInfoSelectionService.listReasonsSelection(chat_id));
                } else if (textMessage.equals(BUTTON_LIMITED_ANIMAL)) {
                    executeSendMessage(animalInfoSelectionService.arrangementLimitedSelection(chat_id));


                } else if (textMessage.equals(BUTTON_STAGE_2_CAT)) {
                    executeSendMessage(keyboardMarkup.boardMarkupStageTwoCat(chat_id));
                } else if (textMessage.equals(BUTTON_ARRANGEMENT_CAT)) {
                    executeSendMessage(catInfoSelectionService.kittenArrangementSelection(chat_id));
                } else if (textMessage.equals(BUTTON_ARRANGEMENT_BIG_CAT)) {
                    executeSendMessage(catInfoSelectionService.arrangementAdultSelectionCat(chat_id));


                } else if (textMessage.equals(BUTTON_STAGE_2_DOG)) {
                    executeSendMessage(keyboardMarkup.boardMarkupStageTwoDog(chat_id));
                } else if (textMessage.equals(BUTTON_ARRANGEMENT_DOG)) {
                    executeSendMessage(dogInfoSelectionService.arrangementPuppy(chat_id));
                } else if (textMessage.equals(BUTTON_ARRANGEMENT_BIG_DOG)) {
                    executeSendMessage(dogInfoSelectionService.arrangementAdultDog(chat_id));
                } else if (textMessage.equals(BUTTON_ADVICE_DOGHANDLER)) {
                    executeSendMessage(dogInfoSelectionService.advicesDogHandler(chat_id));
                } else if (textMessage.equals(BUTTON_RECOMMENDATION_DOGHANDLER)) {
                    executeSendMessage(dogInfoSelectionService.recommendationsForProvenDogHandlers(chat_id));

                    // report Сдача ОТЧЕТА!
                } else if (textMessage.equals(BUTTON_REPORT_CAT) || textMessage.equals(BUTTON_REPORT_DOG)) {
                    executeSendMessage(keyboardMarkup.boardMarkupStageThree(chat_id));
                } else if (textMessage.equals(BUTTON_REPORT_FORM)) {
                    executeSendMessage(new SendMessage(chat_id, "Пришлите фото питомца!"));
                    photoCheckButton = true;

                    Client clientRep = clientRepository.findClientByFirstName(update.message().chat().firstName());

                    if (clientRep == null) {
                        Client client = new Client();
                        client.setFirstName(update.message().chat().firstName());
                        client.setChatId(chat_id);
                        client.setTookAPet(true);
                        client.setDateTimeToTook(LocalDateTime.now());
                        clientRepository.save(client);
                    } else {
                        clientRep.setTookAPet(true);
                        clientRep.setChatId(chat_id);
                        clientRepository.save(clientRep);
                    }

//                Сдача текстовой части отчета!
                } else if (reportCheckButton) {

                    String photoName = "C:/photoAnimal/" + update.message().chat().firstName();
                    Report report = reportRepository.findReportByPhotoNameIdAndGeneralWellBeingAndCheckReport(photoName,null,false);
                    if (report.getGeneralWellBeing() == null) {
                        report.setGeneralWellBeing(update.message().text());
                        reportRepository.save(report);
                    }
                    executeSendMessage(new SendMessage(chat_id,"Отчет сдан! Скоро проверим!"));
                    reportCheckButton = false;
                } else {
                    executeSendMessage(new SendMessage(chat_id, "Не понял Вас."));
                }
                //                Сдача фото отчета!
            } else if (update.message().photo()!=null && photoCheckButton) {

                createDirectoriesAndSavePhoto(update);

                String firstName = update.message().chat().firstName();

                Client clientRep = clientRepository.findClientByFirstName(firstName);

                Report report = new Report();
                report.setDateAdded(LocalDateTime.now());
                report.setPhotoNameId("C:/photoAnimal/" + firstName);
                report.setCheckReport(false);
                if (clientRep != null) {
                    report.setClient(clientRep);
                    clientRep.setDateTimeToTook(LocalDateTime.now());
                    clientRepository.save(clientRep);
                }
                reportRepository.save(report);
                photoCheckButton = false;
                reportCheckButton = true;
                executeSendMessage(new SendMessage(chat_id,
                        "Хорошо! Теперь текстовая часть отчета по пунктам в ОДНОМ сообщении: \n " +
                                "1. Рацион животного. \n " +
                                "2. Общее самочувствие и привыкание к новому месту. \n " +
                                "3. Изменения в поведении: отказ от старых привычек, приобретение новых."));


            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void createDirectoriesAndSavePhoto(Update update) {
        // Получение информации о фото
        Message message = update.message(); // Получите сообщение с фото от бота
        String firstName = message.chat().firstName();

        PhotoSize photo = Arrays.stream(message.photo())
                .max(Comparator.comparing(PhotoSize::fileSize))
                .orElse(null);
        String fileId = Objects.requireNonNull(photo).fileId();

        // Получение пути к фото
        GetFileResponse fileResponse = telegramBot.execute(new GetFile(fileId));
        String filePath = fileResponse.file().filePath();
        //Создание директории
        Path filePathDir = Path.of(photoAnimalDir, firstName + "/" + fileId +  "." +getFileExtension(filePath));
        try {
            Files.createDirectories(filePathDir.getParent());
            Files.deleteIfExists(filePathDir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Сохранение фото в директорию
        String fileUrl = "https://api.telegram.org/file/bot6956888569:AAEFrZelVSc43_vrzZ6vVG1rNio1lePqzVc/" + filePath;
        String savePath = "C:/photoAnimal/" + firstName + "/" + fileId + "." + getFileExtension(filePath);
        saveFile(fileUrl, savePath);
    }

    private String getFileExtension(String filePath) {
        int lastDotIndex = filePath.lastIndexOf('.');
        if (lastDotIndex != -1) {
            return filePath.substring(lastDotIndex + 1);
        } else {
            return null;
        }
    }
    private static void saveFile(String fileUrl, String savePath) {
        try {
            URL url = new URL(fileUrl);
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(savePath);
            byte[] b = new byte[1024];
            int length;
            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }
            is.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void executeSendMessage(SendMessage sendMessage) {
        telegramBot.execute(sendMessage);
    }
    private void executePhoto(SendPhoto sendPhoto) {
        telegramBot.execute(sendPhoto);
    }

    public void executeSendMessage(Long chatId, String messageText) {
        SendMessage sendMessage = new SendMessage(chatId, messageText);
        telegramBot.execute(sendMessage);
    }
}