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
    public interface ErrorHandling {
        interface ErrorCodes {
            final String EY_0001 = "EY_0001";
            final String EY_0002 = "EY_0002";
            final String EY_0003 = "EY_0003";
            String EY_0004 = "EY_0004";
        }

        interface ErrorName {
            final String EY_0001 = "INVALID_STATUS_TRANSFER";
            final String EY_0002 = "PORTER_HAD_CHOSEN_CURRENT_ORDER";
            final String EY_0003 = "PORTER_HAD_INPUTED_TIME_IN_INCORRECT_FORMAT";
            String EY_0004 = "EMAIL_HAS_INVALID_FORMAT";
        }

        interface ErrorDescription {
            final String EY_0001 = "Cannot execute action for order in RECRUITMENT_COMPLETED status";
            final String EY_0002 = "Porter had been chosen current order for execute";
            String EY_0003 = "Porter had inputed time value in incorrect format";
            String EY_0004 = "Email has invalid format";
        }
    }

    public interface Notifications {
        String UNFORTUNATELY_ALL_WORKERS_WERE_FOUND = "К сожалению, нужное число человек уже откликнулось на данный заказ. Ожидайте следующий заказ.";
        String ORDER_RECRUITMENT_COMPLETED_FOR_PORTERS = "Формирование заказа %s завершено";
        String ORDER_RECRUITMENT_COMPLETED_FOR_CUSTOMER = "Для выполнения заказа %s найдено необходимое число рабочих";
        String INPUT_TIME_START = "В какое время вы готовы начать работу";
        String DUPLICATE_TIMETABLE_FOR_DAY = "Вы уже определи график на работы на %s. Вы точно хотите определить новый график (старый график будет безвозвратно потерян)?";

        String FINISH_COMPLETE_TIMETABLE = "Вы обозначили следующий график работы:\n";

        String PORTER_HAD_CHOSEN_CURRENT_ORDER = "Вы уже выбрали заказ № %s для выполнения";
        String INPUT_YOR_EMAIL = "Введите email";
    }

    public interface OrderCreationQuestions {
        Map<Integer, String> CREATE_ORDER_QUESTIONS = ImmutableMap.of(
                0, "Дата заказа",
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
        InlineKeyboardMarkup SELECT_ROLE_KEYBOARD = new InlineKeyboardMarkup().setKeyboard(ImmutableList.of(InlineButtons.ButtonsLines.SELECT_ROLE_BTN_LINE, InlineButtons.ButtonsLines.I_HAVE_ACCOUNT));
        InlineKeyboardMarkup SELECT_CUSTOMER_ACTION_KEYBOARD = new InlineKeyboardMarkup().setKeyboard(ImmutableList.of(InlineButtons.ButtonsLines.SELECT_CUSTOMER_ACTION_BTN_LINE));
        InlineKeyboardMarkup SELECT_PORTER_ACTION_KEYBOARD = new InlineKeyboardMarkup().setKeyboard(ImmutableList.of(InlineButtons.ButtonsLines.SELECT_PORTER_ACTION_BTN_LINE));
        InlineKeyboardMarkup FULL_SELECT_PORTER_ACTION_KEYBOARD = new InlineKeyboardMarkup().setKeyboard(ImmutableList.of(InlineButtons.ButtonsLines.SELECT_PORTER_SHOW_TIMETABLE, InlineButtons.ButtonsLines.SELECT_PORTER_ACTION_BTN_LINE));
        InlineKeyboardMarkup PORTER_TIMETABLE_ACTION_KEYBOARD = new InlineKeyboardMarkup().setKeyboard(ImmutableList.of(InlineButtons.ButtonsLines.PORTER_TIMETABLE_BTN_LINE, InlineButtons.ButtonsLines.PORTER_TIMETABLE_CONFIRM_BTN_LINE));
        InlineKeyboardMarkup PORTER_TIMETABLE_CHANGE_ACTION_KEYBOARD = new InlineKeyboardMarkup().setKeyboard(ImmutableList.of(InlineButtons.ButtonsLines.PORTER_TIMETABLE_CHANGE_CONFIRM_BTN_LINE, InlineButtons.ButtonsLines.PORTER_TIMETABLE_CANCEL_CONFIRM_BTN_LINE));
        InlineKeyboardMarkup INPUT_EMAIL_ACTION_KEYBOARD = new InlineKeyboardMarkup().setKeyboard(ImmutableList.of(InlineButtons.ButtonsLines.EMAIL_BTN_LINE));
    }

    public interface InlineButtons {
        interface ButtonsLines {
            List<InlineKeyboardButton> SELECT_ROLE_BTN_LINE = ImmutableList.of(Templates.SELECT_PORTER_INL_BTN, Templates.SELECT_CUSTOMER_INL_BTN);
            List<InlineKeyboardButton> SELECT_CUSTOMER_ACTION_BTN_LINE = ImmutableList.of(Templates.MAKE_ORDER_INL_BTN);
            List<InlineKeyboardButton> SELECT_PORTER_ACTION_BTN_LINE = ImmutableList.of(Templates.PORTER_CHANGE_TIMETABLE_INL_BTN);
            List<InlineKeyboardButton> PORTER_TIMETABLE_BTN_LINE = ImmutableList.of(Templates.MONDAY_TIMETABLE_INL_BTN,
                    Templates.TUESADAY_TIMETABLE_INL_BTN, Templates.WENSDAY_TIMETABLE_INL_BTN,
                    Templates.THUESDAY_TIMETABLE_INL_BTN, Templates.FRIDAY_TIMETABLE_INL_BTN,
                    Templates.SATURDAY_TIMETABLE_INL_BTN, Templates.SUNDAY_TIMETABLE_INL_BTN);
            List<InlineKeyboardButton> PORTER_TIMETABLE_CONFIRM_BTN_LINE = ImmutableList.of(Templates.PORTER_TIMATABLE_CONFIRM_INL_BTN);
            List<InlineKeyboardButton> PORTER_TIMETABLE_CHANGE_CONFIRM_BTN_LINE = ImmutableList.of(Templates.PORTER_TIMATABLE_CHANGE_CONFIRM_INL_BTN);
            List<InlineKeyboardButton> PORTER_TIMETABLE_CANCEL_CONFIRM_BTN_LINE = ImmutableList.of(Templates.PORTER_TIMATABLE_CHANGE_CANCEK_INL_BTN);
            List<InlineKeyboardButton> I_HAVE_ACCOUNT = ImmutableList.of(Templates.I_HAVE_ACCOUNT_INL_BTN);
            List<InlineKeyboardButton> EMAIL_BTN_LINE = ImmutableList.of(Templates.INPUT_EMAIL_INL_BTN, Templates.CANCEL_INPUT_EMAIL_INL_BTN);
            List<InlineKeyboardButton> SELECT_PORTER_SHOW_TIMETABLE = ImmutableList.of(Templates.SELECT_PORTER_SHOW_TIMETABLE_INL_BTN);
        }

        interface Templates {
            public final InlineKeyboardButton SELECT_PORTER_INL_BTN = ElementsHelper.createInlineButton(Texts.SELECT_PORTER, Commands.SELECT_PORTER_CMD);
            public final InlineKeyboardButton SELECT_CUSTOMER_INL_BTN = ElementsHelper.createInlineButton(Texts.SELECT_CUSTOMER, Commands.SELECT_CUSTOMER_CMD);
            public final InlineKeyboardButton MAKE_ORDER_INL_BTN = ElementsHelper.createInlineButton(Texts.CUSTOMER_MAKE_ORDER, Commands.CUSTOMER_MAKE_ORDER_CMD);
            InlineKeyboardButton PORTER_CHANGE_TIMETABLE_INL_BTN = ElementsHelper.createInlineButton(Texts.PORTER_CHANGE_TIMETABLE, Commands.PORTER_CHANGE_TIMETABLE_CMD);

            InlineKeyboardButton MONDAY_TIMETABLE_INL_BTN = ElementsHelper.createInlineButton(Texts.Days.MONDAY, Commands.MONDAY_SELECT_TIMETABLE);
            InlineKeyboardButton TUESADAY_TIMETABLE_INL_BTN = ElementsHelper.createInlineButton(Texts.Days.TUESADAY, Commands.TUESADAY_SELECT_TIMETABLE);
            InlineKeyboardButton WENSDAY_TIMETABLE_INL_BTN = ElementsHelper.createInlineButton(Texts.Days.WENSDAY, Commands.WENSDAY_SELECT_TIMETABLE);
            InlineKeyboardButton THUESDAY_TIMETABLE_INL_BTN = ElementsHelper.createInlineButton(Texts.Days.THUESDAY, Commands.THUESDAY_SELECT_TIMETABLE);
            InlineKeyboardButton FRIDAY_TIMETABLE_INL_BTN = ElementsHelper.createInlineButton(Texts.Days.FRIDAY, Commands.FRIDAY_SELECT_TIMETABLE);
            InlineKeyboardButton SATURDAY_TIMETABLE_INL_BTN = ElementsHelper.createInlineButton(Texts.Days.SATURDAY, Commands.SATURDAY_SELECT_TIMETABLE);
            InlineKeyboardButton SUNDAY_TIMETABLE_INL_BTN = ElementsHelper.createInlineButton(Texts.Days.SUNDAY, Commands.SUNDAY_SELECT_TIMETABLE);

            InlineKeyboardButton PORTER_TIMATABLE_CONFIRM_INL_BTN = ElementsHelper.createInlineButton(Texts.PORTER_CONFIRM_TIMETABLE_BTN, Commands.PORTER_CONFIRM_TIMETABLE);
            InlineKeyboardButton PORTER_TIMATABLE_CHANGE_CONFIRM_INL_BTN = ElementsHelper.createInlineButton(Texts.PORTER_CONFIRM_TIMETABLE_CHANGE_BTN, Commands.PORTER_CONFIRM_TIMETABLE_CHANGE);
            InlineKeyboardButton PORTER_TIMATABLE_CHANGE_CANCEK_INL_BTN = ElementsHelper.createInlineButton(Texts.PORTER_CANCEL_CHANGE_TIMETABLE_BTN, Commands.PORTER_CANCEL_TIMETABLE_CHANGE);
            InlineKeyboardButton I_HAVE_ACCOUNT_INL_BTN = ElementsHelper.createInlineButton(Texts.I_HAVE_ACCOUNT_BTN, Commands.I_HAVE_ACCOUNT_CMD);
            InlineKeyboardButton INPUT_EMAIL_INL_BTN = ElementsHelper.createInlineButton(Texts.INPUT_EMAIL_BTN, Commands.INPUT_EMAIL_CMD);
            InlineKeyboardButton CANCEL_INPUT_EMAIL_INL_BTN = ElementsHelper.createInlineButton(Texts.CANCEL_INPUT_EMAIL, Commands.CANCEL_INPUT_EMAIL_CMD);
            InlineKeyboardButton SELECT_PORTER_SHOW_TIMETABLE_INL_BTN = ElementsHelper.createInlineButton(Texts.SELECT_PORTER_SHOW_TIMETABLE, Commands.SELECT_PORTER_SHOW_TIMETABLE_CMD);
        }

        public interface Commands {
            public final String SELECT_PORTER_CMD = "SELECT_PORTER";
            public final String SELECT_CUSTOMER_CMD = "SELECT_CUSTOMER";

            public final String CUSTOMER_MAKE_ORDER_CMD = "CUSTOMER_MAKE_ORDER";

            public final String PORTER_CHANGE_TIMETABLE_CMD = "PORTER_CHANGE_TIMETABLE";

            public final String PORTER_EXECUTE_ORDER = "REQUEST_EXECUTE_ORDER_%s";
            public final String PORTER_EXECUTE_ORDER_REGEX = "REQUEST_EXECUTE_ORDER_";

            public final String DAY_SELECT_TIMETABLE_REGEX = "(_SELECT_FOR_PORTER)$";

            public final String MONDAY_SELECT_TIMETABLE = "0_SELECT_FOR_PORTER";
            public final String TUESADAY_SELECT_TIMETABLE = "1_SELECT_FOR_PORTER";
            public final String WENSDAY_SELECT_TIMETABLE = "2_SELECT_FOR_PORTER";
            public final String THUESDAY_SELECT_TIMETABLE = "3_SELECT_FOR_PORTER";
            public final String FRIDAY_SELECT_TIMETABLE = "4_SELECT_FOR_PORTER";
            public final String SATURDAY_SELECT_TIMETABLE = "5_SELECT_FOR_PORTER";
            public final String SUNDAY_SELECT_TIMETABLE = "6_SELECT_FOR_PORTER";

            String PORTER_CONFIRM_TIMETABLE = "7_SELECT_FOR_PORTER";
            String PORTER_CONFIRM_TIMETABLE_CHANGE = "CONFIRM_TIMETABLE_CHANGE";
            String PORTER_CANCEL_TIMETABLE_CHANGE = "CANCEL_TIMETABLE_CHANGE";
            String I_HAVE_ACCOUNT_CMD = "I_HAVE_ACCOUNT";
            String INPUT_EMAIL_CMD = "INPUT_EMAIL";
            String CANCEL_INPUT_EMAIL_CMD = "CANCEL_INPUT_EMAIL";
            String SELECT_PORTER_SHOW_TIMETABLE_CMD = "PORTER_SHOW_TIMETABLE";
        }

        public interface Texts {
            public final String SELECT_PORTER = "Я грузчик";
            public final String SELECT_CUSTOMER = "Я заказчик";

            public final String CUSTOMER_MAKE_ORDER = "Создать заказ";

            public final String PORTER_CHANGE_TIMETABLE = "Изменить график работы";

            public final String ORDER_NOTIFICATION_TEMPLATE = "Новый заказ\n Дата: %s\n Количество человек: %s\n Время работы: %s\n Почасовая оплата для одного человека: %s";

            String PORTER_WANTS_TO_EXECUTE_ORDER = "Я хочу выполнить этот заказ";

            String PORTER_SELECT_TIMETABLE = "Выберите день, когда вы готовы работать";

            String PORTER_FINISH_DATE = "До скольки вы готовы работать?";

            String DAY_TIMETABLE_RESULT = "%s:\n вы работаете с %s до %s";
            String PORTER_INVALID_TIME_FORMAT = "Введите время в формате ЧЧ24-ММ";
            String I_HAVE_ACCOUNT_BTN = "Уже есть акканут";
            String INPUT_EMAIL_BTN = "Ввести email";
            String CANCEL_INPUT_EMAIL = "Отмена";
            String PORTER_CONFIRM_TIMETABLE_CHANGE_BTN = "Да, хочу изменить";
            String SELECT_PORTER_SHOW_TIMETABLE = "Показать график работы";

            interface Days {
                final String MONDAY = "Пн";
                final String TUESADAY = "Вт";
                final String WENSDAY = "Ср";
                final String THUESDAY = "Чт";
                final String FRIDAY = "Пт";
                final String SATURDAY = "Сб";
                final String SUNDAY = "Вс";

                Map<Integer, String> DAY_ID_AND_DAY = ImmutableMap.<Integer, String>builder()
                        .put(0, MONDAY)
                        .put(1, TUESADAY)
                        .put(2, WENSDAY)
                        .put(3, THUESDAY)
                        .put(4, FRIDAY)
                        .put(5, SATURDAY)
                        .put(6, SUNDAY)
                        .build();
            }

            String PORTER_CONFIRM_TIMETABLE_BTN = "Расписание сформировано";
            String PORTER_CANCEL_CHANGE_TIMETABLE_BTN = "Отменить";

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
