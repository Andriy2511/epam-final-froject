package com.example.finalproject.command.factory;

import com.example.finalproject.command.CommandEnum;
import com.example.finalproject.command.ICommand;
import jakarta.servlet.http.HttpServletRequest;

public class CommandFactory {
    private CommandFactory() {

    }
    public static ICommand getCommand(HttpServletRequest req) {
        String command = req.getParameter("command");
        ICommand iCommand;
        if (command != null) {
            try {
                iCommand = CommandEnum.valueOf(command).getCommand();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                iCommand = CommandEnum.ERROR_PAGE.getCommand();
            }
        } else {
            iCommand = CommandEnum.ERROR_PAGE.getCommand();
        }
        return iCommand;
    }
}
