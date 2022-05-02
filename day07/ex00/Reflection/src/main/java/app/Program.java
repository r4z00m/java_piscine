package app;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

public class Program {

    public static final String PACKAGE = "classes";
    public static final String LINE = "---------------------";
    public static final String FIELD_FOR_CHANGING = "Enter name of the field for changing:";
    public static final String ERROR_CAN_T_FIND_FIELD = "Error: can't find field";
    public static final String ENTER_STRING_VALUE = "Enter String value:";
    public static final String LET_S_CREATE_AN_OBJECT = "Let's create an object.";
    public static final String ENTER_CLASS_NAME = "Enter class name:";
    public static final String ENTER_NAME_OF_THE_METHOD_FOR_CALL = "Enter name of the method for call:";
    public static final String ERROR_THERE_IS_NO_SUCH_CLASS = "Error: there is no such class!";
    public static final String ERROR_WRONG_INPUT_TYPE = "Error: wrong input type!";
    public static final String ERROR_CAN_T_FIND_SUCH_METHOD = "Error: can't find such method";
    public static final String VOID = "void";
    public static final String METHOD_RETURNED = "Method returned:";
    public static final String OBJECT_UPDATED = "Object updated: ";
    public static final String OBJECT_CREATED = "Object created: ";
    public static final String METHODS = "methods:";
    public static final String TO_STRING = "toString";
    public static final String FIELDS = "fields :";
    public static final String INT = "int";
    public static final String DOUBLE = "double";
    public static final String BOOLEAN = "boolean";
    public static final String LONG = "long";
    public static final String CLASSES = "Classes:";
    public static final String INTEGER = "integer";

    public static void main(String[] args) {
        Reflections reflections = new Reflections(PACKAGE, new SubTypesScanner(false));
        Set<Class<?>> set = reflections.getSubTypesOf(Object.class);

        List<String> classes = set.stream()
                .map(Class::getSimpleName)
                .collect(Collectors.toList());

        System.out.println(CLASSES);

        for (String aClass : classes) {
            System.out.println(aClass);
        }

        System.out.println(LINE);

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println(ENTER_CLASS_NAME);

            String result = scanner.nextLine();

            if (!classes.contains(result)) {
                System.err.println(ERROR_THERE_IS_NO_SUCH_CLASS);
                return;
            }

            Class<?> clazz = Class.forName(PACKAGE + "." + result);

            System.out.println(LINE);

            ArrayList<String> strings = printFields(clazz);

            printMethods(clazz);

            System.out.println(LINE);

            Object object = createInstance(strings, clazz, scanner);

            if (object == null) {
                return;
            }

            System.out.println(LINE);

            updateObject(object, scanner, strings);

            System.out.println(LINE);

            callMethod(object, scanner);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void callMethod(Object object, Scanner scanner) {
        System.out.println(ENTER_NAME_OF_THE_METHOD_FOR_CALL);

        String methodName = scanner.nextLine();

        Method[] oMethods = object.getClass().getDeclaredMethods();

        for (Method oMethod : oMethods) {
            String returnType = oMethod.getReturnType().getSimpleName();

            String name = oMethod.getName();

            Class<?>[] parameters = oMethod.getParameterTypes();

            String res;

            if (parameters.length > 0) {
                StringBuilder stringBuilder = new StringBuilder(parameters[0].getSimpleName());

                for (int i = 1; i < parameters.length; i++) {
                    stringBuilder.append(", ");
                    stringBuilder.append(parameters[i].getSimpleName());
                }

                res = name + "(" + stringBuilder + ")";
            } else {
                res = name + "()";
            }

            if (res.equals(methodName)) {
                ArrayList<Object> objects = new ArrayList<>();

                for (Class<?> parameter : parameters) {
                    System.out.println("Enter " + parameter.getSimpleName() + " value:");

                    try {
                        switch (parameter.getSimpleName().toLowerCase()) {
                            case INT:
                            case INTEGER:
                                objects.add(Integer.parseInt(scanner.nextLine()));
                                break;
                            case DOUBLE:
                                objects.add(Double.parseDouble(scanner.nextLine()));
                                break;
                            case BOOLEAN:
                                objects.add(Boolean.parseBoolean(scanner.nextLine()));
                                break;
                            case LONG:
                                objects.add(Long.parseLong(scanner.nextLine()));
                                break;
                            default:
                                objects.add(scanner.nextLine());
                                break;
                        }
                    } catch (NumberFormatException e) {
                        System.err.println(ERROR_WRONG_INPUT_TYPE);
                        System.exit(1);
                    }
                }

                oMethod.setAccessible(true);

                try {
                    if (returnType.equals(VOID)) {
                        oMethod.invoke(object, objects.toArray());
                    } else {
                        System.out.println(METHOD_RETURNED);
                        System.out.println(oMethod.invoke(object, objects.toArray()));
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }

                oMethod.setAccessible(false);
                return;
            }
        }
        System.err.println(ERROR_CAN_T_FIND_SUCH_METHOD);
    }

    private static void updateObject(Object object, Scanner scanner, ArrayList<String> strings) {
        System.out.println(FIELD_FOR_CHANGING);

        String field = scanner.nextLine();

        if (!strings.contains(field)) {
            System.err.println(ERROR_CAN_T_FIND_FIELD);
            System.exit(1);
        }

        System.out.println(ENTER_STRING_VALUE);

        String value = scanner.nextLine();

        try {
            Field fieldForUpdate = object.getClass().getDeclaredField(field);

            fieldForUpdate.setAccessible(true);

            try {
                switch (fieldForUpdate.getType().getSimpleName().toLowerCase()) {
                    case INT:
                    case INTEGER:
                        fieldForUpdate.set(object, Integer.parseInt(value));
                        break;
                    case DOUBLE:
                        fieldForUpdate.set(object, Double.parseDouble(value));
                        break;
                    case BOOLEAN:
                        fieldForUpdate.set(object, Boolean.parseBoolean(value));
                        break;
                    case LONG:
                        fieldForUpdate.set(object, Long.parseLong(value));
                        break;
                    default:
                        fieldForUpdate.set(object, value);
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.err.println(ERROR_WRONG_INPUT_TYPE);
                System.exit(1);
            }

            fieldForUpdate.setAccessible(false);

            System.out.println(OBJECT_UPDATED + object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static Object createInstance(ArrayList<String> strings, Class<?> clazz, Scanner scanner) {
        System.out.println(LET_S_CREATE_AN_OBJECT);

        Constructor<?> constructor = null;

        for (Constructor<?> c : clazz.getConstructors()) {
            if (c.getParameterTypes().length > 0) {
                constructor = c;
                break;
            }
        }

        ArrayList<Object> params = new ArrayList<>();

        if (constructor != null) {
            Parameter[] parameters = constructor.getParameters();

            for (int i = 0; i < parameters.length; i++) {
                System.out.println(strings.get(i) + ":");
                params.add(getParamObject(parameters[i], scanner));
            }

            if (!(params.get(params.size() - 1) instanceof String)) {
                scanner.nextLine();
            }

            try {
                Object object = constructor.newInstance(params.toArray());
                System.out.println(OBJECT_CREATED + object);
                return object;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static Object getParamObject(Parameter parameter, Scanner scanner) {
        String paramName = parameter.getType().getSimpleName().toLowerCase();
        try {
            switch (paramName) {
                case "string":
                    return scanner.nextLine();
                case "int":
                case "integer":
                    return scanner.nextInt();
                case "boolean":
                    return scanner.nextBoolean();
                case "long":
                    return scanner.nextLong();
                case "double":
                    return scanner.nextDouble();
                default:
                    System.err.println("Error");
                    System.exit(-1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void printMethods(Class<?> clazz) {
        System.out.println(METHODS);

        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            if (!method.getName().equals(TO_STRING)) {
                String returnType = method.getReturnType().getSimpleName();

                String name = method.getName();

                Class<?>[] parameters = method.getParameterTypes();

                if (parameters.length > 0) {
                    StringBuilder stringBuilder = new StringBuilder(parameters[0].getSimpleName());

                    for (int i = 1; i < parameters.length; i++) {
                        stringBuilder.append(", ");
                        stringBuilder.append(parameters[i].getSimpleName());
                    }

                    System.out.println("\t" + returnType + " " + name + "(" + stringBuilder + ")");
                } else {
                    System.out.println("\t" + returnType + " " + name + "()");
                }
            }
        }
    }

    private static ArrayList<String> printFields(Class<?> clazz) {
        ArrayList<String> strings = new ArrayList<>();

        System.out.println(FIELDS);

        for (Field field : clazz.getDeclaredFields()) {
            strings.add(field.getName());
            System.out.println("\t" + field.getType().getSimpleName() + " " + field.getName());
        }
        return strings;
    }
}
