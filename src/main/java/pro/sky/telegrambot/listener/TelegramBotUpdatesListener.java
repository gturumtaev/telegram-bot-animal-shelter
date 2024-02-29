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
import pro.sky.telegrambot.service.Impl.DogInfoService;
import pro.sky.telegrambot.service.Impl.ShelterCatServiceImpl;
import pro.sky.telegrambot.service.Impl.ShelterDogServiceImpl;

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
    private final GeneralInfoService generalInfoService;
    private final KeyboardMarkup keyboardMarkup;
    private final ShelterCatServiceImpl shelterCatService;
    private final ShelterDogServiceImpl shelterDogService;
    private final CatInfoService catInfoService;
    private final DogInfoService dogInfoService;

    private ReportRepository reportRepository;
    private ClientRepository clientRepository;

    private final ShelterKeyboardMarkup shelterKeyboardMarkup;
    private boolean isWaitNumber = false;
    private boolean photoCheckButton = false;
    private boolean reportCheckButton = false;
    @Autowired
    public TelegramBotUpdatesListener(@Value("${path.to.photo.folder}") String photoAnimalDir,
                                      GeneralInfoService generalInfoService,
                                      KeyboardMarkup keyboardMarkup,
                                      ShelterCatServiceImpl shelterCatService, ShelterDogServiceImpl shelterDogService, CatInfoService catInfoService,
                                      DogInfoService dogInfoService,
                                      ReportRepository reportRepository, ClientRepository clientRepository, ShelterKeyboardMarkup shelterKeyboardMarkup) {
        this.photoAnimalDir = photoAnimalDir;
        this.generalInfoService = generalInfoService;
        this.keyboardMarkup = keyboardMarkup;
        this.shelterCatService = shelterCatService;
        this.shelterDogService = shelterDogService;
        this.catInfoService = catInfoService;
        this.dogInfoService = dogInfoService;
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

                //cat
                } else if (textMessage.equals(BUTTON_CAT_SHELTER)
                        || textMessage.equals(BUTTON_TO_THE_BEGINNING_CAT)) {
                    executeSendMessage(keyboardMarkup.boardMarkupStageZeroCat(chat_id));
                } else if (textMessage.equals(BUTTON_INFO_CAT_SHELTER)) {
                    executeSendMessage(keyboardMarkup.boardMarkupStageOneCat(chat_id));
                } else if (textMessage.equals(BUTTON_ABOUT_CAT_SHELTER)) {
                    executeSendMessage(shelterCatService.shelterStory(chat_id));
                } else if (textMessage.equals(BUTTON_MODE_CAT_SHELTER)) {
                    executeSendMessage(shelterCatService.getWorkScheduleFromDB(chat_id));
                } else if (textMessage.equals(BUTTON_ADDRESS_CAT_SHELTER)) {
                    executeSendMessage(shelterCatService.getAddressFromDB(chat_id));
                } else if (textMessage.equals(BUTTON_SCHEME_CAT_SHELTER)) {
                    executePhoto(shelterCatService.getDrivingDirections(chat_id));
                } else if (textMessage.equals(BUTTON_PASS_CAT_SHELTER)) {
                    executeSendMessage(shelterCatService.getShelterPhoneNumberSecurityFromDB(chat_id));
                } else if (textMessage.equals(BUTTON_SAFETY_CAT_SHELTER)) {
                    executeSendMessage(shelterCatService.getShelterSafetyPrecautionsSecurityFromDB(chat_id));
                } else if (textMessage.equals(BUTTON_PHONE_CAT_SHELTER)
                        || textMessage.equals(BUTTON_PHONE_DOG_SHELTER)) {
                    executeSendMessage(shelterKeyboardMarkup.contactSelection(chat_id));
                    isWaitNumber = true;
                } else if (textMessage.matches("^\\+\\d{1,} \\d{3} \\d{3} \\d{2} \\d{2}$") && isWaitNumber) {
                    executeSendMessage(shelterKeyboardMarkup.saveClientContact(chat_id, update));
                    isWaitNumber = false;
                } else if (textMessage.equals(BUTTON_VOLUNTEER_CAT_SHELTER)) {
                    executeSendMessage(shelterCatService.getVolunteersShelter(chat_id));

                //dog
                } else if (textMessage.equals(BUTTON_DOG_SHELTER)
                        || textMessage.equals(BUTTON_TO_THE_BEGINNING_DOG)) {
                    executeSendMessage(keyboardMarkup.boardMarkupStageZeroDog(chat_id));
                } else if (textMessage.equals(BUTTON_INFO_DOG_SHELTER)) {
                    executeSendMessage(keyboardMarkup.boardMarkupStageOneDog(chat_id));
                } else if (textMessage.equals(BUTTON_ABOUT_DOG_SHELTER)) {
                    executeSendMessage(shelterDogService.shelterStory(chat_id));
                } else if (textMessage.equals(BUTTON_MODE_DOG_SHELTER)) {
                    executeSendMessage(shelterDogService.getWorkScheduleFromDB(chat_id));
                } else if (textMessage.equals(BUTTON_ADDRESS_DOG_SHELTER)) {
                    executeSendMessage(shelterDogService.getAddressFromDB(chat_id));
                } else if (textMessage.equals(BUTTON_SCHEME_DOG_SHELTER)) {
                    executePhoto(shelterDogService.getDrivingDirections(chat_id));
                } else if (textMessage.equals(BUTTON_PASS_DOG_SHELTER)) {
                    executeSendMessage(shelterDogService.getShelterPhoneNumberSecurityFromDB(chat_id));
                } else if (textMessage.equals(BUTTON_SAFETY_DOG_SHELTER)) {
                    executeSendMessage(shelterDogService.getShelterSafetyPrecautionsSecurityFromDB(chat_id));
                } else if (textMessage.equals(BUTTON_VOLUNTEER_DOG_SHELTER)) {
                    executeSendMessage(shelterDogService.getVolunteersShelter(chat_id));

                //general info
                } else if (textMessage.equals(BUTTON_DATING_RULES)) {
                    executeSendMessage(generalInfoService.dateRules(chat_id));
                } else if (textMessage.equals(BUTTON_TRANSPORTATION_RECOMMENDATION)) {
                    executeSendMessage(generalInfoService.transportationRecommendation(chat_id));
                } else if (textMessage.equals(BUTTON_LIST_DOCUMENTS)) {
                    executeSendMessage(generalInfoService.documentsList(chat_id));
                } else if (textMessage.equals(BUTTON_REASONS_REFUSAL)) {
                    executeSendMessage(generalInfoService.listReasons(chat_id));
                } else if (textMessage.equals(BUTTON_LIMITED_ANIMAL)) {
                    executeSendMessage(generalInfoService.arrangementLimitedPet(chat_id));

                //cat info
                } else if (textMessage.equals(BUTTON_STAGE_2_CAT)) {
                    executeSendMessage(keyboardMarkup.boardMarkupStageTwoCat(chat_id));
                } else if (textMessage.equals(BUTTON_ARRANGEMENT_CAT)) {
                    executeSendMessage(catInfoService.arrangementKitty(chat_id));
                } else if (textMessage.equals(BUTTON_ARRANGEMENT_BIG_CAT)) {
                    executeSendMessage(catInfoService.arrangementAdultCat(chat_id));

                //dog info
                } else if (textMessage.equals(BUTTON_STAGE_2_DOG)) {
                    executeSendMessage(keyboardMarkup.boardMarkupStageTwoDog(chat_id));
                } else if (textMessage.equals(BUTTON_ARRANGEMENT_DOG)) {
                    executeSendMessage(dogInfoService.arrangementPuppy(chat_id));
                } else if (textMessage.equals(BUTTON_ARRANGEMENT_BIG_DOG)) {
                    executeSendMessage(dogInfoService.arrangementAdultDog(chat_id));
                } else if (textMessage.equals(BUTTON_ADVICE_DOGHANDLER)) {
                    executeSendMessage(dogInfoService.advicesDogHandler(chat_id));
                } else if (textMessage.equals(BUTTON_RECOMMENDATION_DOGHANDLER)) {
                    executeSendMessage(dogInfoService.recommendationsForProvenDogHandlers(chat_id));


                //report
                } else if (textMessage.equals(BUTTON_REPORT_CAT)) {
                    executeSendMessage(keyboardMarkup.boardMarkupStageThreeCat(chat_id));
                } else if (textMessage.equals(BUTTON_REPORT_DOG)) {
                    executeSendMessage(keyboardMarkup.boardMarkupStageThreeDog(chat_id));
                } else if (textMessage.equals(BUTTON_REPORT_FORM)) {
                    executeSendMessage(new SendMessage(chat_id, "Пришлите фото питомца!"));
                    photoCheckButton = true;

                    Client clientRep = clientRepository.findClientByFirstName(update.message().chat().firstName());
                    if (clientRep == null) {
                        Client client = new Client();
                        client.setFirstName(update.message().chat().firstName());
                        client.setChatId(chat_id);
                        client.setDateTimeToTook(LocalDateTime.now());
                        clientRepository.save(client);
                    } else {
                        clientRep.setTookAPet(true);
                        clientRep.setChatId(chat_id);
                        clientRepository.save(clientRep);
                    }

                //report text
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

            //report photo
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
        Message message = update.message();
        String firstName = message.chat().firstName();

        PhotoSize photo = Arrays.stream(message.photo())
                .max(Comparator.comparing(PhotoSize::fileSize))
                .orElse(null);
        String fileId = Objects.requireNonNull(photo).fileId();

        GetFileResponse fileResponse = telegramBot.execute(new GetFile(fileId));
        String filePath = fileResponse.file().filePath();
        Path filePathDir = Path.of(photoAnimalDir, firstName + "/" + fileId +  "." +getFileExtension(filePath));
        try {
            Files.createDirectories(filePathDir.getParent());
            Files.deleteIfExists(filePathDir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String fileUrl = "https://api.telegram.org/file/bot6828877043:AAEyz9qsp8KuxLXlqEOlyWizd6O5-Ro6GAE/" + filePath;
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