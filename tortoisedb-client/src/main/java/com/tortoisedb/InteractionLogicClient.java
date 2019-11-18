package com.tortoisedb;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class InteractionLogicClient {

    private enum State{STRT, SETT, GETT, DELT, UPDT, EXST, EXIT, DEFA}
    private final int COMMAND_SIZE = 4;

    private final Protocol protocol;
    private boolean isRunning;
    private State state;
    private final TerminalInterface terminalInterface;
    private String user;
    private final Scanner sc;
    private String newCommand;

    public InteractionLogicClient(final Socket socket) throws IOException{
        this.protocol           = new Protocol(socket);
        this.state              = State.STRT;
        this.terminalInterface  = new TerminalInterface();
        this.isRunning          = true;
        this.sc = new Scanner(System.in);
    }

    public void run() throws IOException {
        try{
            while(this.isRunning){
                switch (state){
                    case STRT:
                        this.caseSTRT();
                        break;
                    case SETT:
                        this.caseSETT();
                        break;
                    case GETT:
                        this.caseGETT();
                        break;
                    case DELT:
                        this.caseDELT();
                        break;
                    case UPDT:
                        this.caseUPDT();
                        break;
                    case EXST:
                        this.caseEXST();
                        break;
                    case EXIT:
                        this.caseEXIT();
                        break;
                    case DEFA:
                        System.out.println("ERROR 401: wrong typed command.");
                        break;
                }
                if(this.isRunning) {
                    this.getAndCheckCommand();
                }
            }
        } catch (final NullPointerException ex){
            this.terminalInterface.printErrorMessage("ERROR 403: Undefined error.");
            this.isRunning = false;
        }
    }

    private void getAndCheckCommand() {
        this.newCommand = sc.nextLine();

        if (newCommand.length() < this.COMMAND_SIZE) {
            state = null;
        } else {
            state = checkCommand(newCommand.substring(0, this.COMMAND_SIZE));
        }

        if (state == null) {
            state = State.DEFA;
        }
    }

    private void caseUPDT() throws IOException {
        if (newCommand.charAt(4) == ' ' && newCommand.charAt(6) == ' ' && newCommand.length() > 7) {
            this.protocol.update(newCommand.charAt(5), newCommand.substring(7));
            System.out.println(this.protocol.read_buffer());
        } else {
            System.out.println("ERROR 402: Unexpected format.");
        }
    }

    private void caseEXIT() throws IOException {
        if (newCommand.length() == COMMAND_SIZE) {
            this.protocol.exit();
            this.isRunning = false;
        } else {
            System.out.println("ERROR 402: Unexpected format.");
        }
    }

    private void caseEXST() throws IOException {
        if (this.checkChatAtPosition4and5()) {
            this.protocol.exist(newCommand.charAt(5));
            final String exists = this.protocol.read_buffer();

            if (exists.charAt(exists.length() - 1) == '1') {
                System.out.println("The key exists.");
            } else {
                System.out.println("The key does not exists.");
            }
        } else {
            System.out.println("ERROR 402: Unexpected format.");
        }
    }

    private void caseSTRT() throws IOException {
        this.protocol.start(user);
        System.out.println(this.protocol.getCommand());
        this.state = State.EXIT;
    }

    private void caseSETT() throws IOException {
        if (newCommand.charAt(4) == ' ' && newCommand.charAt(6) == ' ' && newCommand.length() > 7) {
            this.protocol.set(newCommand.charAt(5), newCommand.substring(7));
            System.out.println(this.protocol.read_buffer());
        } else {
            System.out.println("ERROR 402: Unexpected format.");
        }
    }

    private void caseGETT() throws IOException {
        if (this.checkChatAtPosition4and5()) {
            this.protocol.get(newCommand.charAt(5));
            System.out.println(this.protocol.read_buffer());
        } else {
            System.out.println("ERROR 402: Unexpected format.");
        }
    }

    private void caseDELT() throws IOException {
        if (this.checkChatAtPosition4and5()) {
            this.protocol.delete(newCommand.charAt(5));
            System.out.println(this.protocol.read_buffer());
        } else {
            System.out.println("ERROR 402: Unexpected format.");
        }
    }

    private boolean checkChatAtPosition4and5() {
        return this.newCommand.charAt(4) == ' ' && this.newCommand.length() > 5;
    }

    public void setUser(final String user){
        this.user = user;
    }

    public State checkCommand(String command){
        command = command.toUpperCase();
        for(final State s:State.values()){
            if(s.name().equals(command))
               return s;
        }

        return null;
    }
}
