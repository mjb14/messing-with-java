

public class Solver {

    private SearchNode searchNode;
    private MinPQ<SearchNode> pq;
    private MinPQ<SearchNode> twin;
    private Stack<Board> solution = new Stack<Board>();
    private int moves = 0;
    private boolean result;
    private SearchNode min, twinMin;

    private class SearchNode implements Comparable<SearchNode> {

        private Board board = null;
        //private int moves = 0;
        private int priority;
        private int manhattanScore = 0;
        private SearchNode prev = null;

        private void setPrev(SearchNode p) {
            this.prev = p;
        }

        private int moves() {
            int moves = 0;
            SearchNode temp = this;
            while (temp.prev != null) {
                moves++;
                temp = temp.prev;
            }
            return moves;
        }

        private SearchNode(Board b) {
            this.board = b;
        }

        private int getPriority() {
            if (this.priority > 0)
                return this.priority;

            this.priority = this.board.manhattan() * 100 + this.moves();
            return this.priority;
        }

        private int getManhattan() {
            if (this.manhattanScore > 0)
                return this.manhattanScore;

            this.manhattanScore = this.board.manhattan();
            return this.manhattanScore;
        }

        public int compareTo(SearchNode that) {
            if (this.getPriority() < that.getPriority()) {
                return -1;
            } else if (this.getPriority() > that.getPriority()) {
                return +1;
            } else {
                // Same priority, base off manhattan score
                if (this.getManhattan() < that.getManhattan()) {
                    return -1;
                } else if (this.getManhattan() > that.getManhattan()) {
                    return +1;
                } else {
                    return 0;
                }
            }
        }
    }

    private void aStar() {

        if (!pq.min().board.isGoal() && !twin.min().board.isGoal()) {

            if (pq.size() > 0)
                min = pq.delMin();

            if (twin.size() > 0)
                twinMin = twin.delMin();


            if (!min.board.isGoal() && !twinMin.board.isGoal()) {
                for (Board b : min.board.neighbors()) {
                    if(!b.equals(min.board)) {
                        searchNode = new SearchNode(b);
                        searchNode.setPrev(min);
                        System.out.print(searchNode.board);
                        pq.insert(searchNode);
                    }
                }

                for (Board b : twinMin.board.neighbors()) {
                    if(!b.equals(twinMin.board)) {
                        searchNode = new SearchNode(b);
                        twin.insert(searchNode);
                    }
                }

                this.aStar();

            } else {
                if (twinMin.board.isGoal()) {
                    this.result = false;
                    solution = new Stack<Board>();
                } else {
                    this.result = true;
                    buildSolution(min);
                }

            }
        } else {
            if (twin.min().board.isGoal()) {
                this.result = false;
                solution = new Stack<Board>();
            } else {
                this.result = true;
                buildSolution(pq.min());
            }
        }

    }

    public Solver(Board initial) {
        // Initial our priority queue
        this.pq = new MinPQ<SearchNode>(initial.dimension() * initial.dimension() + 1);
        searchNode = new SearchNode(initial);
        pq.insert(searchNode);

        // Initialize priroity queue of our tiwn board
        this.twin = new MinPQ<SearchNode>(initial.dimension() * initial.dimension() + 1);
        searchNode = new SearchNode(initial.twin());
        twin.insert(searchNode);

        this.aStar();
    }

    public boolean isSolvable() {
        // is the initial board solvable?
        return this.result;
    }

    public int moves() {
        // min number of moves to solve initial board; -1 if no solution
        if (this.isSolvable()) {
            return this.pq.min().moves();
        } else
            return -1;
    }


    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        return this.solution;
    }


    private void buildSolution(SearchNode s) {
        SearchNode temp = s;
        while (temp.prev != null) {
            solution.push(temp.board);
            temp = temp.prev;
        }
        solution.push(temp.board);
    }

    public static void main(String[] args) {
        // solve a slider puzzle (given below)
        // create initial board from file

        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");

        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            System.out.println();

            for (Board board : solver.solution()) {
                StdOut.println(board);
                //System.out.println();
                //System.out.println("hamming: " + board.hamming());
                //System.out.println("manhattan: " + board.manhattan());
                //System.out.println("empty space: " + board.getEmptySquare());
                // for (Board b : board.neighbors()) {
                //      System.out.println("Neighbors:");
                //      StdOut.println(b);
                //  }

            }
        }

    }

}