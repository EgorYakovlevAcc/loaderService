package com.bot;

import com.google.common.collect.ImmutableList;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;


public final class BotModel {
    public interface Messages {
        public final String HELLO_ANONYMOUS = "Добро пожаловать!";
        public final String HELLO_KNOWN = "С возвращением! {}";
    }

    public interface InlineKeyboards {
        InlineKeyboardMarkup SELECT_ROLE_KEYBOARD = new InlineKeyboardMarkup().setKeyboard(ImmutableList.of(InlineButtons.ButtonsLines.SELECT_ROLE_BTN_LINE));
        InlineKeyboardMarkup SELECT_CUSTOMER_ACTION_KEYBOARD = new InlineKeyboardMarkup().setKeyboard(ImmutableList.of(InlineButtons.ButtonsLines.SELECT_CUSTOMER_ACTION_BTN_LINE));
        InlineKeyboardMarkup SELECT_PORTER_ACTION_KEYBOARD = new InlineKeyboardMarkup().setKeyboard(ImmutableList.of(InlineButtons.ButtonsLines.SELECT_PORTER_ACTION_BTN_LINE));
    }

    public interface InlineButtons {
        interface ButtonsLines {
            List<InlineKeyboardButton> SELECT_ROLE_BTN_LINE = ImmutableList.of(Templates.SELECT_PORTER_INL_BTN, Templates.SELECT_CUSTOMER_INL_BTN);
            List<InlineKeyboardButton> SELECT_CUSTOMER_ACTION_BTN_LINE = ImmutableList.of(Templates.MAKE_ORDER_INL_BTN);
            List<InlineKeyboardButton> SELECT_PORTER_ACTION_BTN_LINE = ImmutableList.of(Templates.PORTER_CHANGE_TIMETABLE_INL_BTN);
        }

        public interface Templates {
            public final InlineKeyboardButton SELECT_PORTER_INL_BTN = ElementsHelper.createInlineButton(Texts.SELECT_PORTER, Commands.SELECT_PORTER_CMD);
            public final InlineKeyboardButton SELECT_CUSTOMER_INL_BTN = ElementsHelper.createInlineButton(Texts.SELECT_CUSTOMER, Commands.SELECT_CUSTOMER_CMD);
            public final InlineKeyboardButton MAKE_ORDER_INL_BTN = ElementsHelper.createInlineButton(Texts.CUSTOMER_MAKE_ORDER, Commands.CUSTOMER_MAKE_ORDER_CMD);
            InlineKeyboardButton PORTER_CHANGE_TIMETABLE_INL_BTN = ElementsHelper.createInlineButton(Texts.PORTER_CHANGE_TIMETABLE, Commands.PORTER_CHANGE_TIMETABLE_CMD);
        }

        public interface Commands {
            public final String SELECT_PORTER_CMD = "SELECT_PORTER";
            public final String SELECT_CUSTOMER_CMD = "SELECT_CUSTOMER";

            public final String CUSTOMER_MAKE_ORDER_CMD = "CUSTOMER_MAKE_ORDER";

            public final String PORTER_CHANGE_TIMETABLE_CMD = "PORTER_CHANGE_TIMETABLE";
        }

        public interface Texts {
            public final String SELECT_PORTER = "Я грузчик";
            public final String SELECT_CUSTOMER = "Я заказчик";

            public final String CUSTOMER_MAKE_ORDER = "Сделать заказ";

            public final String PORTER_CHANGE_TIMETABLE = "Изменить график работы";
        }
    }

    private static class ElementsHelper {
        public static InlineKeyboardButton createInlineButton(String text, String command) {
            if ((StringUtils.isEmpty(text)) || (StringUtils.isEmpty(command))) {
                throw new IllegalArgumentException("Parameters text or command should not be empty for create button");
            }
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(text);
            inlineKeyboardButton.setCallbackData(command);
            return inlineKeyboardButton;
        }
    }

}
