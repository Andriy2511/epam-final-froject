package com.example.finalproject.command.factory;

import com.example.finalproject.command.CommandEnum;
import com.example.finalproject.command.ICommand;
import jakarta.servlet.http.HttpServletRequest;

/**
 * The CommandFactory class is responsible for receiving the command from the "command" parameter.
 */
public class CommandFactory {
    private CommandFactory() {}

    /**
     * The method receives a request and receives a command parameter that is defined in the CommandEnum class.
     * If the command is missing, the ERROR_PAGE command is called.
     * @see CommandEnum
     * @param req HttpServletRequest
     * @return an instance of a class which implements interface ICommand
     */
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
