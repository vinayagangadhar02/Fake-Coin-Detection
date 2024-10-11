import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;

public class Main extends Application {

    static int k = 0;
    private static int[] arr;
    private static int fakeCoinIndex;
    private static int genuineCoinWeight;
    private static int fakeCoinWeight;

    public static void main(String[] args) {

        initCoinWeights();


        Arrays.fill(arr, genuineCoinWeight);
        arr[fakeCoinIndex] = fakeCoinWeight;

        // Launch JavaFX application in a new thread
        new Thread(() -> Application.launch(Main.class, args)).start();

        int index = FakeCoin(arr, 0, arr.length - 1);
        System.out.println("The fake coin weight is " + arr[index] + " at index " + index);
        System.out.println("Real Coin weight is "+k);
        if (k > arr[index]) {
            System.out.println("Fake coin is lighter than the real coin");
        } else {
            System.out.println("Fake coin is heavier than the real coin");
        }
    }

    private static void initCoinWeights() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number of coins : ");
        int n = sc.nextInt();
        arr = new int[n];
        UniqueRandomGenerator obj = new UniqueRandomGenerator(100);
        Random random = new Random(10);

        genuineCoinWeight = obj.random();
        fakeCoinIndex = random.nextInt(10);
        do {
            fakeCoinWeight = obj.random();
        } while (fakeCoinWeight == genuineCoinWeight);
    }

    public static int FakeCoin(int[] arr, int low, int high) {
        printArray(arr, low, high);

        if (low == high) {
            return low;
        }
        if (low == high - 1) {
            if (k == arr[low]) {
                printArray(arr, low+1, high );
                return high;
            } else {
                printArray(arr, low , high-1);
                return low;
            }
        }

        int size = (high - low + 1) / 3;

        int low1 = low;
        int high1 = low + size - 1;

        int low2 = high1 + 1;
        int high2 = low2 + size - 1;

        int low3 = high2 + 1;
        int high3 = high;

        int sum1 = weight(arr, low1, high1);
        int sum2 = weight(arr, low2, high2);
        if (sum1 == sum2) {
            k = arr[low1];
            return FakeCoin(arr, low3, high3);
        } else {
            int sum3 = weight(arr, low3, low3 + size - 1);
            if (sum1 == sum3) {
                k = arr[low1];
                return FakeCoin(arr, low2, high2);
            } else {
                k = arr[low2];
                return FakeCoin(arr, low1, high1);
            }
        }
    }

    private static void printArray(int[] arr, int low, int high) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print("------");
        }
        System.out.println("");
        for (int i = 0; i < low; i++) {
            System.out.print(" |  | ");
        }
        for (int i = low; i <= high; i++) {
            System.out.print(" |" + arr[i] + "| ");
        }
        for (int i = high + 1; i < arr.length; i++) {
            System.out.print(" |  | ");
        }
        System.out.println(" ");

        for (int i = 0; i < arr.length; i++) {
            System.out.print("------");
        }
        System.out.println();
    }

    static int weight(int[] arr, int low, int high) {
        int sum = 0;
        for (int i = low; i <= high; i++) {
            sum += arr[i];
        }
        return sum;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Fake Coin Visualizer");

        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: white;");
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        int numColumns = 20;

        for (int i = 0; i < arr.length; i++) {
            Circle circle = new Circle(20);
            Text text = new Text(String.valueOf(arr[i]));
            text.setFont(new Font(12));

            if (i == fakeCoinIndex) {
                circle.setFill(Color.RED);
            } else {
                circle.setFill(Color.GREEN);
            }

            circle.setStroke(Color.BLACK);
            circle.setStrokeWidth(2);

            GridPane.setHalignment(circle, HPos.CENTER);
            GridPane.setValignment(circle, VPos.CENTER);
            GridPane.setHalignment(text, HPos.CENTER);
            GridPane.setValignment(text, VPos.CENTER);

            int row = i / numColumns;
            int column = i % numColumns;

            gridPane.add(circle, column, row);
            gridPane.add(text, column, row);
        }

        Scene scene = new Scene(gridPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

