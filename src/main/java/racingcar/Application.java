package racingcar;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        List<Car> cars = makeCars();
        int tryCount = getTryCount();

        System.out.println();
        System.out.println("실행 결과");

        for (int i = 0; i < tryCount; i++) {
            moveCars(cars);
            printCars(cars);
        }

        printWinners(cars);
    }

    private static List<Car> makeCars() {
        System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
        String input = Console.readLine();
        String[] names = input.split(",");

        List<Car> cars = new ArrayList<>();

        for (String name : names) {
            if (name.isEmpty()) {
                throw new IllegalArgumentException();
            }

            if (name.chars().anyMatch(Character::isWhitespace)) {
                throw new IllegalArgumentException();
            }

            if (name.length() > 5) {
                throw new IllegalArgumentException();
            }

            checkDuplicate(cars, name);
            cars.add(new Car(name));
        }

        return cars;
    }

    private static void checkDuplicate(List<Car> cars, String name) {
        for (Car car : cars) {
            if (car.getName().equals(name)) {
                throw new IllegalArgumentException();
            }
        }
    }

    private static int getTryCount() {
        System.out.println("시도할 회수는 몇회인가요?");
        String input = Console.readLine();

        int count;
        try {
            count = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }

        if (count <= 0) {
            throw new IllegalArgumentException();
        }

        return count;
    }

    private static void moveCars(List<Car> cars) {
        for (Car car : cars) {
            int number = Randoms.pickNumberInRange(0, 9);
            if (number >= 4) {
                car.move();
            }
        }
    }

    private static void printCars(List<Car> cars) {
        for (Car car : cars) {
            System.out.println(car.getName() + " : " + "-".repeat(car.getPosition()));
        }
        System.out.println();
    }

    private static void printWinners(List<Car> cars) {
        int max = findMaxPosition(cars);
        List<String> winners = new ArrayList<>();

        for (Car car : cars) {
            if (car.getPosition() == max) {
                winners.add(car.getName());
            }
        }

        System.out.println("최종 우승자 : " + String.join(", ", winners));
    }

    private static int findMaxPosition(List<Car> cars) {
        int max = 0;

        for (Car car : cars) {
            if (car.getPosition() > max) {
                max = car.getPosition();
            }
        }

        return max;
    }

    static class Car {
        private final String name;
        private int position;

        public Car(String name) {
            this.name = name;
            this.position = 0;
        }

        public void move() {
            position++;
        }

        public String getName() {
            return name;
        }

        public int getPosition() {
            return position;
        }
    }
}
