
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        processGraph1();
    }

    public static void processGraph1() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input1.txt"));
        int graphNumber = 0;

        while (scanner.hasNextInt()) {
            int n = scanner.nextInt();
            scanner.nextLine();

            graphNumber++;

            if (n < 1 || n > 100) {
                System.out.println("정점 개수 오류");
                break;
            }

            int[][] GraphMatrix_1 = readGraph1(scanner, n);

            System.out.println("그래프 [" + graphNumber + "]");
            System.out.println("----------------------------");

            DM_BFS(GraphMatrix_1, n);

            System.out.println("============================");
        }

        scanner.close();
    }

    public static int[][] readGraph1(Scanner scanner, int n) {
        int[][] GraphMatrix_1 = new int[n + 1][n + 1];

        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            Scanner row = new Scanner(line);

            int vertex = row.nextInt();

            while (row.hasNextInt()) {
                int neighbor = row.nextInt();
                GraphMatrix_1[vertex][neighbor] = 1;
            }

            row.close();
        }

        return GraphMatrix_1;
    }

    public static void DM_BFS(int[][] GraphMatrix_1, int n) {
        boolean[] visited = new boolean[n + 1];
        int[] queue = new int[n + 1];

        int front = 0;
        int rear = 0;
        int count = 0;

        visited[1] = true;
        queue[rear++] = 1;

        System.out.println("너비 우선 탐색");

        while (front < rear) {
            int current = queue[front++];
            count++;

            if (count > 1) {
                System.out.print(" - ");
            }

            System.out.print(current);

            for (int i = 1; i <= n; i++) {
                if (GraphMatrix_1[current][i] == 1 && !visited[i]) {
                    visited[i] = true;
                    queue[rear++] = i;
                }
            }
        }

        System.out.println();

        if (count != n) {
            System.out.println("모든 vertex를 방문하지 못했습니다.");
            System.out.print("방문하지 못한 vertex: ");

            for (int i = 1; i <= n; i++) {
                if (!visited[i]) {
                    System.out.print(i + " ");
                }
            }

            System.out.println();
        }
    }
}
