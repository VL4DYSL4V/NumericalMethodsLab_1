package main;

import command.RunCommand;
import framework.application.Application;
import framework.command.RunnableCommand;
import framework.state.ApplicationState;
import state.LaboratoryState;

public class Main {

    private static final String PROPERTIES_PATH_STRING = "/laboratory-framework.properties";

    public static void main(String[] args) {
        ApplicationState state = new LaboratoryState();
        RunnableCommand runnableCommand = new RunCommand();
        Application application = new Application.ApplicationBuilder(PROPERTIES_PATH_STRING, state)
                .addCommand("run", runnableCommand)
                .build();
        application.start();
    }

}
