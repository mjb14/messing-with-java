
public class Board {

    private final int[][] tiles;
    private int emptySquarePosition;

    public Board(int[][] blocks) {
        // construct a board from an N-by-N array of blocks
        this.tiles = new int[blocks[0].length][blocks[0].length];
        for (int i = 0; i < blocks[0].length; i++)
            for (int j = 0; j < blocks[0].length; j++)
                this.tiles[i][j] = blocks[i][j];

        this.emptySquarePosition = this.getEmptySquare();
    }

    // board dimension N
    public int dimension() {
        return this.tiles[0].length;
    }

    // number of blocks out of place
    public int hamming() {
        int score = 0;
        int pos = 0;
        for (int i = 0; i < this.dimension(); i++) {
            for (int j = 0; j < this.dimension(); j++) {
                pos = this.dimension() * i + j + 1;
                if (this.tiles[i][j] != 0 && tiles[i][j] != pos) {
                    score++;
                }
            }
        }
        return score;
    }

    private int getI(int pos) {
        if (pos <= this.dimension())
            return 0;
        return (int) Math.floor((pos - 1) / this.dimension());
    }

    private int getJ(int pos) {
        if (pos <= this.dimension()) {
            return pos - 1;
        }
        return (pos - 1) % this.dimension();
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int score = 0;
        int pos = 0;
        int val = 0;
        for (int i = 0; i < this.dimension(); i++) {
            for (int j = 0; j < this.dimension(); j++) {
                pos = this.dimension() * i + j + 1;
                if (this.tiles[i][j] != 0 && tiles[i][j] != pos) {
                    score += Math.abs(this.getJ(this.tiles[i][j]) - j);
                    score += Math.abs(this.getI(this.tiles[i][j]) - i);
                }
            }
        }
        // cache score for future use
        return score;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int pos = 0;
        for (int i = 0; i < this.dimension(); i++) {
            for (int j = 0; j < this.dimension(); j++) {
                pos = this.dimension() * i + j + 1;
                if (this.dimension() * this.dimension() != pos) {
                    if (tiles[i][j] != pos) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public Board twin() {
        // a board obtained by exchanging two adjacent blocks in the same row
        int tempPos;
        int tempValue;
        int swapValue;
        int[][] clone = this.getClonedBoard();
        Board tempBoard;

        // if size = 2, swap row that does not have blank value
        if (this.dimension() == 2) {
            tempPos = this.emptySquarePosition;
            if (tempPos < 3) {
                tempValue = clone[1][0];
                swapValue = clone[1][1];
                clone[1][0] = swapValue;
                clone[1][1] = tempValue;
            } else {
                tempValue = clone[0][0];
                swapValue = clone[0][1];
                clone[0][0] = swapValue;
                clone[0][1] = tempValue;
            }

        } else {

            tempPos = this.emptySquarePosition;
            if (this.getI(tempPos) == 0) {
                // if empty is in 1st row, swap in 2nd row
                tempValue = clone[1][0];
                swapValue = clone[1][1];
                clone[1][0] = swapValue;
                clone[1][1] = tempValue;
            } else {
                // swap in 1st row
                tempValue = clone[0][0];
                swapValue = clone[0][1];
                clone[0][0] = swapValue;
                clone[0][1] = tempValue;
            }
        }
        tempBoard = new Board(clone);
        return tempBoard;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;

        if (this.dimension() != that.dimension()) return false;

        for (int i = 0; i < this.dimension(); i++) {
            for (int j = 0; j < this.dimension(); j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    private int[][] getClonedBoard() {
        int[][] clone = new int[this.dimension()][this.dimension()];

        for (int i = 0; i < this.dimension(); i++) {
            for (int j = 0; j < this.dimension(); j++) {
                clone[i][j] = this.tiles[i][j];
            }
        }

        return clone;
    }

    public Iterable<Board> neighbors() {
        // compute neighbors
        int tempPos;
        int tempValue;
        int[][] clone;
        Stack<Board> neighbors = new Stack<Board>();
        Board tempBoard;

        if (this.hasBottomNeighbor()) {
            clone = this.getClonedBoard();
            tempPos = this.emptySquarePosition;
            tempPos = tempPos + this.dimension();
            tempValue = clone[this.getI(tempPos)][this.getJ(tempPos)];
            clone[this.getI(tempPos)][this.getJ(tempPos)] = 0;
            clone[this.getI(this.emptySquarePosition)][this.getJ(this.emptySquarePosition)] = tempValue;
            tempBoard = new Board(clone);
            neighbors.push(tempBoard);
        }


        if (this.hasTopNeighbor()) {
            clone = this.getClonedBoard();
            tempPos = this.emptySquarePosition;
            tempPos = tempPos - this.dimension();
            tempValue = clone[this.getI(tempPos)][this.getJ(tempPos)];
            clone[this.getI(tempPos)][this.getJ(tempPos)] = 0;
            clone[this.getI(this.emptySquarePosition)][this.getJ(this.emptySquarePosition)] = tempValue;
            tempBoard = new Board(clone);
            neighbors.push(tempBoard);
        }

        if (this.hasLeftNeighbor()) {
            clone = this.getClonedBoard();
            tempPos = this.emptySquarePosition;
            tempPos = tempPos - 1;
            tempValue = clone[this.getI(tempPos)][this.getJ(tempPos)];
            clone[this.getI(tempPos)][this.getJ(tempPos)] = 0;
            clone[this.getI(this.emptySquarePosition)][this.getJ(this.emptySquarePosition)] = tempValue;
            tempBoard = new Board(clone);
            neighbors.push(tempBoard);
        }

        if (this.hasRightNeighbor()) {
            clone = this.getClonedBoard();
            tempPos = this.emptySquarePosition;
            tempPos = tempPos + 1;
            tempValue = clone[this.getI(tempPos)][this.getJ(tempPos)];
            clone[this.getI(tempPos)][this.getJ(tempPos)] = 0;
            clone[this.getI(this.emptySquarePosition)][this.getJ(this.emptySquarePosition)] = tempValue;
            tempBoard = new Board(clone);
            neighbors.push(tempBoard);
        }

        return neighbors;
    }

    public String toString() {
        // string representation of the board (in the output format specified below)
        StringBuilder s = new StringBuilder();
        s.append(this.dimension() + "\n");
        for (int i = 0; i < this.dimension(); i++) {
            for (int j = 0; j < this.dimension(); j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }


    private int getEmptySquare() {
        int pos = 0;
        for (int i = 0; i < this.dimension(); i++) {
            for (int j = 0; j < this.dimension(); j++) {
                pos = this.dimension() * i + j + 1;
                if (tiles[i][j] == 0) {
                    //System.out.println("Empty: " + pos);
                    return pos;
                }
            }
        }
        return pos;
    }

    private boolean hasLeftNeighbor() {
        if (this.getJ(this.emptySquarePosition) == 0)
            return false;
        return true;
    }

    private boolean hasRightNeighbor() {
        if (this.getJ(this.emptySquarePosition) < this.dimension() - 1)
            return true;

        return false;
    }

    private boolean hasTopNeighbor() {
        if (this.getI(this.emptySquarePosition) == 0)
            return false;

        return true;
    }

    private boolean hasBottomNeighbor() {
        if (this.getI(this.emptySquarePosition) < this.dimension() - 1)
            return true;
        return false;
    }

}