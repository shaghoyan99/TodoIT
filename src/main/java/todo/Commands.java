package todo;

public interface Commands {

    int LOGIN = 1;
    int REGISTER = 2;
    int EXIT = 0;


    int ADD_TODO = 1;
    int MY_ALL_LIST = 2;
    int MY_TODO_LIST = 3;
    int MY_IN_PROGRESS_LIST = 4;
    int MY_FINISHED_LIST = 5;
    int CHANGE_TODO_STATUS = 6;
    int DELETE_TODO_BY_ID = 7;
    int LOGOUT = 0;


    static void printMainCommands() {
        System.out.println("Please input " + LOGIN + " for LOGIN");
        System.out.println("Please input " + REGISTER + " for REGISTER");
        System.out.println("Please input " + EXIT + " for EXIT");
    }


    static void printUserCommands() {
        System.out.println("Please input " + ADD_TODO + " for ADD_TODO");
        System.out.println("Please input " + MY_ALL_LIST + " for PRINT_MY_ALL_LIST");
        System.out.println("Please input " + MY_TODO_LIST + " for PRINT_MY_TODO_LIST");
        System.out.println("Please input " + MY_IN_PROGRESS_LIST + " for PRINT_MY_PROGRESS_LIST");
        System.out.println("Please input " + MY_FINISHED_LIST + " for PRINT_MY_FINISHED_LIST");
        System.out.println("Please input " + CHANGE_TODO_STATUS + " for CHANGE_MY_TODO_STATUS");
        System.out.println("Please input " + DELETE_TODO_BY_ID + " for DELETE_MY_TODO_BY_ID");
        System.out.println("Please input " + LOGOUT + " for LOGOUT");

    }
}
