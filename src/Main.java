import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class Main {

    static final String SEP = " – ";

    static List<int[][]> readGraphs1(Path file) throws IOException {
        List<String> lines = new ArrayList<>();
        for (String s : Files.readAllLines(file, StandardCharsets.UTF_8)) {
            s = s.replace("", "").trim();
            if (!s.isEmpty()) lines.add(s);
        }
        List<int[][]> graphs = new ArrayList<>();
        int idx = 0;
        while (idx < lines.size()) {
            int n = Integer.parseInt(lines.get(idx++));
            int[][] GraphMatrix_1 = new int[n + 1][n + 1];
            for (int i = 0; i < n; i++) {
                StringTokenizer st = new StringTokenizer(lines.get(idx++));
                int u = Integer.parseInt(st.nextToken());
                while (st.hasMoreTokens()) {
                    int v = Integer.parseInt(st.nextToken());
                    GraphMatrix_1[u][v] = 1;
                }
            }
            graphs.add(GraphMatrix_1);
        }
        return graphs;
    }

    public static int[] DM_DFS(int[][] GraphMatrix_1, int n) {
        boolean[] visited = new boolean[n + 1];
        int[] visitOrder = new int[n];
        int visitCount = recursiveVisit(GraphMatrix_1, n, 1, visited, visitOrder, 0);
        return Arrays.copyOf(visitOrder, visitCount);
    }

    private static int recursiveVisit(int[][] GraphMatrix_1, int n, int current, boolean[] visited, int[] visitOrder, int visitCount) {
        visited[current] = true;
        visitOrder[visitCount++] = current;
        for (int neighbor = 1; neighbor <= n; neighbor++) {
            if (GraphMatrix_1[current][neighbor] != 0 && !visited[neighbor]) {
                visitCount = recursiveVisit(GraphMatrix_1, n, neighbor, visited, visitOrder, visitCount);
            }
        }
        return visitCount;
    }

    public static int[] DM_BFS(int[][] GraphMatrix_1, int n) {
        boolean[] visited = new boolean[n + 1];
        int[] queue = new int[n + 1];
        int[] visitOrder = new int[n];

        int front = 0;
        int rear = 0;
        int count = 0;

        visited[1] = true;
        queue[rear] = 1;
        rear++;

        while (front < rear) {
            int current = queue[front];
            front++;

            visitOrder[count] = current;
            count++;

            for (int i = 1; i <= n; i++) {
                if (GraphMatrix_1[current][i] == 1 && !visited[i]) {
                    visited[i] = true;
                    queue[rear] = i;
                    rear++;
                }
            }
        }

        return Arrays.copyOf(visitOrder, count);
    }

    static String join(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) sb.append(SEP);
            sb.append(arr[i]);
        }
        return sb.toString();
    }

    public static void processGraph1() throws IOException {
        Path in1 = Paths.get("input1.txt");

        System.out.println("1. 그래프 탐방 수행 결과");
        System.out.println();
        List<int[][]> graphs = readGraphs1(in1);
        for (int g = 0; g < graphs.size(); g++) {
            int[][] GraphMatrix_1 = graphs.get(g);
            int n = GraphMatrix_1.length - 1;
            System.out.println("그래프 [" + (g + 1) + "]");
            System.out.println("----------------------------");
            System.out.println("깊이 우선 탐색");
            System.out.println(join(DM_DFS(GraphMatrix_1, n)));
            System.out.println("너비 우선 탐색");
            System.out.println(join(DM_BFS(GraphMatrix_1, n)));
            System.out.println("============================");
            System.out.println();
        }
    }

    // Dijkstra 담당 팀원이 작성한 메서드는 원본 그대로 유지
    public static int countGraph(String filePath) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(filePath));
        int graphCount = 0;
        while (sc.hasNext()) {
            int vertexCount = sc.nextInt();
            sc.nextLine();

            for (int i = 0; i < vertexCount; i++) {
                sc.nextLine();
            }
            graphCount++;
        }
        sc.close();
        return graphCount;
    }

    public static int[][] Read_Input2(Scanner scan) throws FileNotFoundException {
        int[][] matrix;
        Scanner scanner = scan;
        int n = scanner.nextInt();
        scanner.nextLine();
        matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(i == j) matrix[i][j] = 0;
                else matrix[i][j] = -1;
            }
        }

        int point = 0;

        while (point < n) {
            String line = scanner.nextLine();
            String[] parts = line.trim().split("\\s+");

            for (int i = 1; i < parts.length; i += 2) {
                int temp = Integer.parseInt(parts[i]);
                int temp2 = Integer.parseInt(parts[i + 1]);
                matrix[point][temp - 1] = temp2;
            }

            point++;
        }

        return matrix;
    }

    public static int[][][] Solution(String filePath) throws FileNotFoundException {
        int[][][] answer;
        int num = countGraph(filePath);
        Scanner scanner = new Scanner(new File(filePath));
        answer = new int[num][][];

        for(int i = 0; i < num ; i++)
        {
            answer[i] = Read_Input2(scanner);
        }
        return answer;
    }

    public static void Dijkstra_Algorithm(int[][][] matrix)
    {
        final int INF = Integer.MAX_VALUE;

        System.out.println("2. 최단 경로 구하기 수행 결과");

        for (int i = 0; i < matrix.length; i++) {
            int[][] graph = matrix[i];
            int vertexCount = graph.length;
            int start = 0; // 정점 1번

            int[] dist = new int[vertexCount];
            boolean[] visited = new boolean[vertexCount];
            int[] prev = new int[vertexCount];

            //dist 초기화, prev 초기화
            for (int j = 0; j < vertexCount; j++) {
                dist[j] = INF;
                prev[j] = -1;
            }
            dist[start] = 0;

            for (int j = 0; j < vertexCount; j++) {
                int u = -1;
                int minDist = INF;

                // 최단 거리가 아직 확정되지 않은 정점 중 가장 가까운 정점 선택
                for (int k = 0; k < vertexCount; k++) {
                    if (!visited[k] && dist[k] < minDist) {
                        minDist = dist[k];
                        u = k;
                    }
                }

                // 남은 정점에 도달할 수 없으면 종료
                if (u == -1) {
                    break;
                }

                visited[u] = true;

                // u를 거쳐서 k로 가는 경로가 더 짧으면 갱신
                for (int k = 0; k < vertexCount; k++) {
                    int weight = graph[u][k];
                    if (visited[k] || weight == -1) {
                        continue;
                    }

                    int newDist = dist[u] + weight;
                    if (newDist < dist[k]) {
                        dist[k] = newDist;
                        prev[k] = u;
                    }
                }
            }

            System.out.println();
            System.out.println("그래프 ["+(i+1)+"]");
            System.out.println("------------------------------");
            System.out.println("시작점: "+(start+1));

            int[] path = new int[vertexCount];

            for (int target = 0; target < vertexCount; target++) {
                if (target == start) {
                    continue;
                }

                if (dist[target] == INF) {
                    System.out.println("정점 ["+target+1+"]: 도달 불가");
                    continue;
                }

                // 도착점부터 직전 정점을 따라가며 경로를 역순으로 저장
                int length = 0;
                for (int current = target; current != -1; current = prev[current]) {
                    path[length++] = current;
                }

                System.out.print("정점 ["+(target+1)+"]: ");
                for (int k = length - 1; k >= 0; k--) {
                    System.out.print(path[k] + 1);
                    if (k > 0) {
                        System.out.print(" - ");
                    }
                }
                System.out.println(", 길이: "+ dist[target]);
            }

            System.out.println("==============================");
        }
    }

    // 기존 메서드를 변경하지 않고 과제 지정 함수명을 연결
    public static void DM_SP(int[][][] GraphMatrix_2) {
        Dijkstra_Algorithm(GraphMatrix_2);
    }

    public static void processGraph2() throws FileNotFoundException {
        int[][][] answer = Solution("res/input2.txt");
        DM_SP(answer);
    }

    public static void runAll() throws IOException {
        processGraph1();
        processGraph2();
    }

    public static void main(String[] args) throws IOException {
        runAll();
    }
}
