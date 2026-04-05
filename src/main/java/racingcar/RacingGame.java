package racingcar;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.List;

public class RacingGame {

    private static final int MAX_CAR_NAME_LENGTH = 5;
    private static final int MIN_TRY_COUNT = 1;
    private static final int RANDOM_START = 0;
    private static final int RANDOM_END = 9;
    private static final int MOVE_THRESHOLD = 4;

    public void start() {
        List<Car> cars = createCars();
        int tryCount = inputTryCount();

        System.out.print('\n');
        System.out.println("실행 결과");

        for (int i = 0; i < tryCount; i++) {
            moveCars(cars);
            printCars(cars);
        }

        printWinners(cars);
    }

    private List<Car> createCars() {
        System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
        String input = Console.readLine();
        String[] names = input.split(",");

        validateNames(names);

        List<Car> cars = new ArrayList<>();
        for (String name : names) {
            cars.add(new Car(name));
        }
        return cars;
    }

    private int inputTryCount() {
        System.out.println("시도할 회수는 몇회인가요?");
        String input = Console.readLine();
        int count = parseTryCount(input);

        if (count < MIN_TRY_COUNT) {
            throw new IllegalArgumentException();
        }

        return count;
    }

    private int parseTryCount(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }

    private void validateNames(String[] names) {
        List<String> used = new ArrayList<>();

        for (String name : names) {
            validateName(name);

            if (used.contains(name)) {
                throw new IllegalArgumentException();
            }
            used.add(name);
        }
    }

    private void validateName(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (name.chars().anyMatch(Character::isWhitespace)) {
            throw new IllegalArgumentException();
        }

        if (name.length() > MAX_CAR_NAME_LENGTH) {
            throw new IllegalArgumentException();
        }
    }

    private void moveCars(List<Car> cars) {
        for (Car car : cars) {
            int number = Randoms.pickNumberInRange(RANDOM_START, RANDOM_END);
            car.move(number, MOVE_THRESHOLD);
        }
    }

    private void printCars(List<Car> cars) {
        for (Car car : cars) {
            System.out.println(car.getName() + " : " + "-".repeat(car.getPosition()));
        }
        System.out.print('\n');
    }

    private void printWinners(List<Car> cars) {
        int max = findMaxPosition(cars);
        List<String> winners = findWinners(cars, max);
        System.out.println("최종 우승자 : " + String.join(", ", winners));
    }

    private int findMaxPosition(List<Car> cars) {
        int max = 0;
        for (Car car : cars) {
            max = Math.max(max, car.getPosition());
        }
        return max;
    }

    private List<String> findWinners(List<Car> cars, int max) {
        List<String> winners = new ArrayList<>();

        for (Car car : cars) {
            if (car.getPosition() == max) {
                winners.add(car.getName());
            }
        }

        return winners;
    }
}
