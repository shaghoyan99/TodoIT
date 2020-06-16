package todo;

import todo.manager.ToDoManager;
import todo.manager.UserManager;
import todo.model.ToDo;
import todo.model.ToDoStatus;
import todo.model.User;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ToDoMain implements Commands {

    private static final Scanner scanner = new Scanner(System.in);
    private static UserManager userManager = new UserManager();
    private static ToDoManager toDoManager = new ToDoManager();
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static User currentUser;

    public static void main(String[] args) {

        boolean isRun = true;
        while (isRun) {
            Commands.printMainCommands();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case LOGIN:
                    loginUser();
                    break;
                case REGISTER:
                    registerUser();
                    break;
                case EXIT:
                    isRun = false;
                    break;
                default:
                    System.out.println("Wrong Command!");
            }
        }

    }

    private static void registerUser() {

        System.out.println("Please input user data: " +
                "name,surname,email,password");
        try {
            String userDataStr = scanner.nextLine();
            String[] userData = userDataStr.split(",");
            User userByEmail = userManager.getByEmail(userData[2]);
            if (userByEmail == null) {
                User user = new User();
                user.setName(userData[0]);
                user.setSurname(userData[1]);
                user.setEmail(userData[2]);
                user.setPassword(userData[3]);
                if (userManager.register(user)) {
                    System.out.println("User was successfully added");
                } else {
                    System.out.println("Something went wrong!");
                }
            } else {
                System.out.println("User already exists");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Wrong Data!");
        }
    }

    private static void loginUser() {
        System.out.println("Please input email,password");
        try {
            String loginStr = scanner.nextLine();
            String[] loginArray = loginStr.split(",");
            User userByEmailAndPassword = userManager.getByEmailAndPassword(loginArray[0], loginArray[1]);
            if (userByEmailAndPassword != null) {
                currentUser = userByEmailAndPassword;
                loginSuccess();
            } else {
                System.out.println("Wrong email or password");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Wrong data!");
        }

    }

    private static void loginSuccess() {
        System.out.println("Welcome " + currentUser.getName());
        boolean isRun = true;
        while (isRun) {
            Commands.printUserCommands();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case ADD_TODO:
                    addToDo();
                    break;
                case MY_ALL_LIST:
                    printToDos(toDoManager.getAllToDosByUser(currentUser.getId()));
                    break;
                case MY_TODO_LIST:
                    printToDos(toDoManager.getAllToDosByUserByUserStatus(currentUser.getId(), ToDoStatus.TODO));
                    break;
                case MY_IN_PROGRESS_LIST:
                    printToDos(toDoManager.getAllToDosByUserByUserStatus(currentUser.getId(), ToDoStatus.IN_PROGRESS));
                    break;
                case MY_FINISHED_LIST:
                    printToDos(toDoManager.getAllToDosByUserByUserStatus(currentUser.getId(), ToDoStatus.FINISHED));
                    break;
                case CHANGE_TODO_STATUS:
                    changeToDoStatus();
                    break;
                case DELETE_TODO_BY_ID:
                    deleteTodoById();
                    break;
                case LOGOUT:
                    isRun = false;
                    break;
                default:
                    System.out.println("Wrong Command!");
            }
        }
    }

    private static void printToDos(List<ToDo> allToDos) {
        for (ToDo allToDo : allToDos) {
            System.out.println(allToDo);
        }
    }

    private static void addToDo() {
        System.out.println("Please input title,deadline(yyyy-MM-dd HH:mm:ss).");

        String toDoDataStr = scanner.nextLine();
        String[] split = toDoDataStr.split(",");
        ToDo toDo = new ToDo();
        try {
            String titel = split[0];
            toDo.setTitle(titel);
            try {
                if (split[1] != null) {
                    toDo.setDeadline(sdf.parse(split[1]));
                }
                System.out.println("Todo was added!");
            } catch (IndexOutOfBoundsException e) {
            } catch (ParseException e) {
                System.out.println("Please input date bt this format:(yyyy-MM-dd HH:mm:ss)");
            }
            toDo.setStatus(ToDoStatus.TODO);
            toDo.setUser(currentUser);
            if (toDoManager.create(toDo)) {
                System.out.println("ToDo was added");
            } else {
                System.out.println("Somting went wrong");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Wrong data ");
        }
    }

    private static void changeToDoStatus() {
        System.out.println("Please choose TODO from list:");
        List<ToDo> list = toDoManager.getAllToDosByUser(currentUser.getId());
        for (ToDo toDo : list) {
            System.out.println(toDo);
        }
        long id = Long.parseLong(scanner.nextLine());
        ToDo byId = toDoManager.getById(id);
        if (byId.getUser().getId() == currentUser.getId()) {
            System.out.println("Please choose Status");
            System.out.println(Arrays.toString(ToDoStatus.values()));
            ToDoStatus status = ToDoStatus.valueOf(scanner.nextLine());
            if (toDoManager.update(id, status)) {
                System.out.println("Status was changed");
            } else {
                System.out.println("Something went wrong");
            }
        } else {
            System.out.println("Wrong ID");
        }
    }

    private static void deleteTodoById() {
        System.out.println("Please choose TODO from list:");
        List<ToDo> list = toDoManager.getAllToDosByUser(currentUser.getId());
        for (ToDo toDo : list) {
            System.out.println(toDo);
        }
        long id = Long.parseLong(scanner.nextLine());
        ToDo byId = toDoManager.getById(id);
        if (byId.getUser().getId() == currentUser.getId()) {
            toDoManager.delete(id);
        } else {
            System.out.println("Wrong ID");
        }
    }
}
