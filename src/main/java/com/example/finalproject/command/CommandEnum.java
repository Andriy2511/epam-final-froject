package com.example.finalproject.command;

import com.example.finalproject.command.admin.*;
import com.example.finalproject.command.authorization.LoginCommand;
import com.example.finalproject.command.authorization.RegistrationCommand;
import com.example.finalproject.command.catalog.CatalogCommand;
import com.example.finalproject.command.error.ErrorPageCommand;
import com.example.finalproject.command.language.LanguageCommand;
import com.example.finalproject.command.logout.LogOutCommand;
import com.example.finalproject.command.user.UserCardCommand;
import com.example.finalproject.command.user.UserOrderCommand;


public enum CommandEnum {

    ADMIN_ADD_PRODUCT(new AddProductCommand()), ADMIN_CUSTOMER_CONTROLLER(new AdminCustomerCommand()), ADMIN_ORDER_CONTROLLER(new AdminOrderCommand()),
    ADMIN_PRODUCT_CONTROLLER(new AdminProductCommand()), ADMIN_CHANGE_PRODUCT(new AdminChangeProductCommand()),
    LOGIN_CONTROLLER(new LoginCommand()), REGISTRATION_CONTROLLER(new RegistrationCommand()), ERROR_PAGE(new ErrorPageCommand()),
    CATALOG_COMMAND(new CatalogCommand()), LANGUAGE_COMMAND(new LanguageCommand()), LOGOUT_COMMAND(new LogOutCommand()), USER_ORDER_COMMAND(new UserOrderCommand()),
    USER_CARD_COMMAND(new UserCardCommand());

    private ICommand command;

    CommandEnum(ICommand command) {
            this.command = command;
        }

    public ICommand getCommand() {
        return command;
    }

}
