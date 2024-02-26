package pro.sky.telegrambot.constans;

import com.vdurmont.emoji.EmojiParser;

public class Constans {

    //Emojis
    public final static String EMOJI_SMILEY = EmojiParser.parseToUnicode(":smiley:");
    public final static String EMOJI_WAVE = EmojiParser.parseToUnicode(":wave:");
    public final static String EMOJI_POINT_DOWN = EmojiParser.parseToUnicode(":point_down:");
    public final static String EMOJI_CAT = EmojiParser.parseToUnicode(":cat:");
    public final static String EMOJI_DOG = EmojiParser.parseToUnicode(":dog:");

    //Button text
    public final static String BUTTON_CAT_SHELTER = EMOJI_CAT + " Приют для кошек";
    public final static String BUTTON_DOG_SHELTER = EMOJI_DOG + " Приют для собак";


    //Info text
    public final static String WELLCOME_MESSAGE = "Привет! Я бот который помогает найти бездомным животным своих любящих хозяев. Выберите в меню нужный вам приют";
}
