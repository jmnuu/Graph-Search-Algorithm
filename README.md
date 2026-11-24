# Graph-Search-Algorithm
이산수학

Main.java   
│  
├── main()  
│   └── runAll()  
│       │  
│       ├── processGraph1()              // DFS, BFS 실행  
│       │   ├── readGraphs1()            // input1.txt 읽기  
│       │   ├── DM_DFS()                 // 깊이 우선 탐색  
│       │   │   └── recursiveVisit()     // 재귀 탐색  
│       │   ├── DM_BFS()                 // 너비 우선 탐색  
│       │   └── join()                   // 탐색 결과 출력 형식  
│       │  
│       └── processGraph2()              // Dijkstra 실행  
│           ├── Solution()              // 그래프 데이터 생성  
│           │   ├── countGraph()        // 그래프 개수 확인  
│           │   └── Read_Input2()       // input2.txt 읽기  
│           └── DM_SP()                 // 최단 경로 탐색  
│  
├── input1.txt                           // DFS, BFS 입력  
└── input2.txt                           // Dijkstra 입력  

  - `Dijkstra_Algorithm()` → `DM_SP()` : 함수명 통일
- `runAll()` 호출 : 실행 구조 단순
- `processGraph1()`, `processGraph2()` 추가 : 기능별 분리
- 파일 경로 및 출력 형식 → 통일
- DFS, Dijkstra 핵심 알고리즘 → 변경 안했습니다.
