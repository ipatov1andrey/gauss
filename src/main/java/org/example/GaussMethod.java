package org.example;
import java.util.Scanner;

public class GaussMethod {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите количество уравнений: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        double[][] matrix = new double[n][n + 1];
        for (int i = 0; i < n; i++) {
            System.out.print("Введите уравнение " + (i + 1) + " в формате '1 2 3 4': ");
            String equation = scanner.nextLine();
            String[] parts = equation.split("\\s+");
            for (int j = 0; j < n + 1; j++) {
                matrix[i][j] = parseTerm(parts[j]);
            }
        }

        double[] result = gauss(matrix);
        System.out.println("\nРешение:");
        for (int i = 0; i < result.length; i++) {
            System.out.println("x" + (i + 1) + " = " + result[i]);
        }

        scanner.close();
    }

    private static double parseTerm(String term) {
        if (term.matches("-?\\d+[a-z]")) {
            return Double.parseDouble(term.replaceAll("[a-z]", ""));
        } else if (term.matches("[a-z]")) {
            return 1.0;
        } else if (term.matches("-[a-z]")) {
            return -1.0;
        } else {
            return Double.parseDouble(term);
        }
    }


    private static double[] gauss(double[][] matrix) {
        int n = matrix.length;

        for (int i = 0; i < n; i++) {
            // Поиск строки с максимальным элементом в текущем столбце
            int maxRow = i;
            for (int k = i + 1; k < n; k++) {
                if (Math.abs(matrix[k][i]) > Math.abs(matrix[maxRow][i])) {
                    maxRow = k;
                }
            }

            // Обмен строк для получения ненулевого элемента на диагонали
            double[] temp = matrix[i];
            matrix[i] = matrix[maxRow];
            matrix[maxRow] = temp;

            // Приведение к диагональному виду
            for (int k = i + 1; k < n; k++) {
                double factor = matrix[k][i] / matrix[i][i];
                for (int j = i; j < n + 1; j++) {
                    matrix[k][j] -= factor * matrix[i][j];
                }
            }
        }

        // Обратный ход метода Гаусса
        double[] solution = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            solution[i] = matrix[i][n] / matrix[i][i];
            for (int k = i - 1; k >= 0; k--) {
                matrix[k][n] -= matrix[k][i] * solution[i];
            }
        }

        // Округление до сотых
        for (int i = 0; i < n; i++) {
            solution[i] = Math.round(solution[i] * 100.0) / 100.0;
        }

        return solution;
    }

}