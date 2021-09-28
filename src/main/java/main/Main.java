package main;

import command.RelaxationCommand;
import command.ModifiedNewtonCommand;
import framework.application.Application;
import framework.state.ApplicationState;
import state.LaboratoryState;

public class Main {

    private static final String PROPERTIES_PATH_STRING = "/laboratory-framework.properties";

    public static void main(String[] args) {
        ApplicationState state = new LaboratoryState();
        Application application = new Application.ApplicationBuilder(PROPERTIES_PATH_STRING, state)
                .addCommand("modified-newton", new ModifiedNewtonCommand())
                .addCommand("relaxation", new RelaxationCommand())
                .build();
        application.start();
    }

}
