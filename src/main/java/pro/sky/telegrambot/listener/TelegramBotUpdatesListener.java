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
import com.vdurmont.emoji.EmojiParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.sky.telegrambot.model.Client;
import pro.sky.telegrambot.model.Report;
import pro.sky.telegrambot.report.CatReportMarkup;
import pro.sky.telegrambot.report.DogReportMarkup;
import pro.sky.telegrambot.repository.ClientRepository;
import pro.sky.telegrambot.repository.ReportRepository;
import pro.sky.telegrambot.service.*;
import pro.sky.telegrambot.markup.HelloKeyboardMarkup;
import pro.sky.telegrambot.markup.ShelterKeyboardMarkup;

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
    private final DogReportMarkup dogReportMarkup;
    private final CatReportMarkup catReportMarkup;
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
                                      DogReportMarkup dogReportMarkup, CatReportMarkup catReportMarkup,
                                      CatShelterServiceImpl catShelterService,
                                      CatInfoSelectionService catInfoSelectionService,
                                      DogInfoSelectionServiceImpl dogInfoSelectionService,
                                      ReportRepository reportRepository, ClientRepository clientRepository, ShelterKeyboardMarkup shelterKeyboardMarkup) {
        this.photoAnimalDir = photoAnimalDir;
        this.dogShelterMarkup = dogShelterService;
        this.animalInfoSelectionService = animalInfoSelectionService;
        this.dogReportMarkup = dogReportMarkup;
        this.catReportMarkup = catReportMarkup;
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
        String smile_dog = EmojiParser.parseToUnicode(":dog:");
        String smile_cat = EmojiParser.parseToUnicode(":cat2:");
        String smile_arrow = EmojiParser.parseToUnicode(":arrow_left:");

        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            String textMessage = update.message().text();
            Long chat_id = update.message().chat().id();

            if(textMessage!=null){
                if (textMessage.equals("/start")
                        || textMessage.equals(smile_arrow + " В начало")
                        || textMessage.equals("В начало")) {
                    executeSendMessage(HelloKeyboardMarkup.boardMarkupCatAndDog(chat_id));

                } else if (textMessage.equals(BUTTON_CAT_SHELTER)
                        || textMessage.equals(smile_cat + " В начало")) {
                    executeSendMessage(catShelterServiceImpl.boardMarkup(chat_id));

                } else if (textMessage.equals(smile_cat + " Узнать информацию о приюте")) {
                    executeSendMessage(catShelterServiceImpl.shelterBoardMarkup(chat_id));
                } else if (textMessage.equals(smile_cat + "Рассказать о приюте")) {
                    executeSendMessage(catShelterServiceImpl.shelterStory(chat_id));
                } else if (textMessage.equals(smile_cat + "Расписание работы")) {
                    executeSendMessage(catShelterServiceImpl.getWorkScheduleFromDB(chat_id));
                } else if (textMessage.equals(smile_cat + "Адрес приюта")) {
                    executeSendMessage(catShelterServiceImpl.getAddressFromDB(chat_id));
                } else if (textMessage.equals(smile_cat + "Схема проезда")) {
                    executePhoto(catShelterServiceImpl.getDrivingDirections(chat_id));
                } else if (textMessage.equals(smile_cat + "Оформление пропуска")) {
                    executeSendMessage(catShelterServiceImpl.getShelterPhoneNumberSecurityFromDB(chat_id));
                } else if (textMessage.equals(smile_cat + "Техника безопасности")) {
                    executeSendMessage(catShelterServiceImpl.getShelterSafetyPrecautionsSecurityFromDB(chat_id));
                } else if (textMessage.equals(smile_cat + "Оставить номер телефона")
                        || textMessage.equals(smile_dog + "Оставить номер телефона")
                        || textMessage.equals("Оставить номер телефона")) {
                    executeSendMessage(shelterKeyboardMarkup.contactSelection(chat_id));
                    isWaitNumber = true;
                } else if (textMessage.matches("^\\+\\d{1,} \\d{3} \\d{3} \\d{2} \\d{2}$") && isWaitNumber) {
                    executeSendMessage(shelterKeyboardMarkup.saveClientContact(chat_id, update));
                    isWaitNumber = false;
                } else if (textMessage.equals(smile_cat + " Связаться с волонтером")) {
                    executeSendMessage(catShelterServiceImpl.getVolunteersShelter(chat_id));

                } else if (textMessage.equals(BUTTON_DOG_SHELTER)
                        || textMessage.equals(smile_dog + " В начало")) {
                    executeSendMessage(dogShelterMarkup.boardMarkup(chat_id));

                } else if (textMessage.equals(smile_dog + " Узнать информацию о приюте")) {
                    executeSendMessage(dogShelterMarkup.shelterBoardMarkup(chat_id));
                } else if (textMessage.equals(smile_dog + "Рассказать о приюте")) {
                    executeSendMessage(dogShelterMarkup.shelterStory(chat_id));
                } else if (textMessage.equals(smile_dog + "Расписание работы")) {
                    executeSendMessage(dogShelterMarkup.getWorkScheduleFromDB(chat_id));
                } else if (textMessage.equals(smile_dog + "Адрес приюта")) {
                    executeSendMessage(dogShelterMarkup.getAddressFromDB(chat_id));
                } else if (textMessage.equals(smile_dog + "Схема проезда")) {
                    executePhoto(dogShelterMarkup.getDrivingDirections(chat_id));
                } else if (textMessage.equals(smile_dog + "Оформление пропуска")) {
                    executeSendMessage(dogShelterMarkup.getShelterPhoneNumberSecurityFromDB(chat_id));
                } else if (textMessage.equals(smile_dog + "Техника безопасности")) {
                    executeSendMessage(dogShelterMarkup.getShelterSafetyPrecautionsSecurityFromDB(chat_id));
                } else if (textMessage.equals(smile_dog + " Связаться c волонтером")) {
                    executeSendMessage(dogShelterMarkup.getVolunteersShelter(chat_id));


                } else if (textMessage.equals("Правила знакомства")) {
                    executeSendMessage(animalInfoSelectionService.datingRulesSelection(chat_id));
                } else if (textMessage.equals("Рекомендации по транспортировке")) {
                    executeSendMessage(animalInfoSelectionService.transportationSelection(chat_id));
                } else if (textMessage.equals("Список документов")) {
                    executeSendMessage(animalInfoSelectionService.documentsSelection(chat_id));
                } else if (textMessage.equals("Список причин для отказа")) {
                    executeSendMessage(animalInfoSelectionService.listReasonsSelection(chat_id));
                } else if (textMessage.equals("Обустройство для ограниченного")) {
                    executeSendMessage(animalInfoSelectionService.arrangementLimitedSelection(chat_id));

                } else if (textMessage.equals(smile_cat + " Как взять животное из приюта")) {
                    executeSendMessage(catInfoSelectionService.selectionInfo(chat_id));
                } else if (textMessage.equals("Обустройство котенка")) {
                    executeSendMessage(catInfoSelectionService.kittenArrangementSelection(chat_id));
                } else if (textMessage.equals("Обустройство для взрослого кота")) {
                    executeSendMessage(catInfoSelectionService.arrangementAdultSelectionCat(chat_id));


                } else if (textMessage.equals(smile_dog + " Как взять животное из приюта")) {
                    executeSendMessage(dogInfoSelectionService.selectionInfo(chat_id));
                } else if (textMessage.equals("Обустройство щенка")) {
                    executeSendMessage(dogInfoSelectionService.puppyArrangementSelection(chat_id));
                } else if (textMessage.equals("Обустройство для взрослой собаки")) {
                    executeSendMessage(dogInfoSelectionService.arrangementAdultSelectionDog(chat_id));
                } else if (textMessage.equals("Советы кинолога по первичному общению с собакой")) {
                    executeSendMessage(dogInfoSelectionService.tipsHandlerInitialCommunicationDog(chat_id));
                } else if (textMessage.equals("Рекомендации по проверенным кинологам")) {
                    executeSendMessage(dogInfoSelectionService.recommendationsForProvenDogHandlers(chat_id));

                    // report Сдача ОТЧЕТА!
                } else if (textMessage.equals(smile_cat + " Прислать отчет о питомце")) {
                    executeSendMessage(catReportMarkup.selectionInfo(chat_id));
                } else if (textMessage.equals("Форма ежедневного отчета")) {
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

                }else if (textMessage.equals(smile_dog + " Прислать отчет о питомце")) {
                    executeSendMessage(dogReportMarkup.selectionInfo(chat_id));

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