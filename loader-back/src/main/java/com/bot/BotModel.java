package com.bot;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


public final class BotModel {
    public interface OrderCreationQuestions {
        Map<Integer, String> CREATE_ORDER_QUESTIONS = ImmutableMap.of(0, "Дата заказа",
                1, "Колличество грузчиков",
                2, "Время выполнения заказа",
                3, "Почасовая оплата одного грузчика");
    }
    public interface DefaultValues {
        Double PAY_FOR_AN_HOUR = 100.0;
        Double ORDER_EXECUTING_TIME = 4.0;
        Integer AMOUNT_OF_PORTERS = 1;
        Date ORDER_EXECUTION_DATE = ElementsHelper.getDefaultDate();
    }

    public interface Messages {
        public final String HELLO_ANONYMOUS = "Добро пожаловать!";
        public final String SELECT_ACTIONS = "Выберите действие";
        public final String ORDER_CREATION_FINISHED = "Заказ успешно создан";
    }

    public interface UserTypeStr {
        String USER_TYPE_CUSTOMER = "CUSTOMER";
        String USER_TYPE_PORTER = "PORTER";
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

        interface Templates {
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

            public final String ORDER_NOTIFICATION_TEMPLATE = "Новый заказ\n Дата: %s\n Колличество человек: %s\n Время работы: %s\n Почасовая оплата для одного человека: %s";
        }
    }

    private static class ElementsHelper {
        static Date getDefaultDate() {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + 1);
            return calendar.getTime();
        }
        static InlineKeyboardButton createInlineButton(String text, String command) {
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
