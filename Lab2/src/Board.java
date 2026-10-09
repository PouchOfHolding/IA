import java.util.ArrayList;
import java.util.List;

class Board implements Ilayout, Cloneable {
    private static final int dim = 3;
    private int board[][];

    public Board() {

        board = new int[dim][dim];
    }

    public Board(String str) throws IllegalStateException {
        if (str.length() != dim * dim)
            throw new IllegalStateException("Invalid arg in Board constructor");
        board = new int[dim][dim];
        int si = 0;
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                board[i][j] = Character.getNumericValue(str.charAt(si++));
    }

    public String toString() {
        // TODO: Three rows, 0 as a space, newline after each row.
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < board.length; i++){  // cada Linha

            for(int j = 0; j < board[i].length; j++){ // cada coluna de cada linha
                if(board[i][j] == 0) sb.append(" ");
                else sb.append(board[i][j]);

            }

            sb.append(System.lineSeparator()); //o mesmo que fazer \n

        }

        return sb.toString();
    }

    public boolean equals(Object o) {
        // TODO: Compare tile positions; handle null/other types.

        if (this == o) return true;

        if (o == null || this.getClass() != o.getClass()) return false;

        Board that = (Board) o; //cast para tipo Board

        for(int i = 0; i < board.length; i++){

            for(int j = 0; j < board[i].length; j++){

                if( this.board[i][j] != that.board[i][j]) return false;
            }
        }
        return true;

    }

    public int hashCode() {
        // TODO: Equal boards must have equal hash codes.

        return toString().hashCode();

    }

    // These three stubs make explicit the methods required by Ilayout.
    public List<Ilayout> children() {
        // TODO: Make independent boards for all legal moves.

        List<Ilayout> sucs = new ArrayList<>();
        int Xlinha = -1;
        int Xcoluna = -1;

        for(int i = 0; i < board.length; i++){

            for(int j = 0; j < board[i].length; j++){

                if(board[i][j] == 0){
                    Xlinha = i;
                    Xcoluna = j;
                    break;
                }
            }
        }

        if (Xlinha > 0){

            Board child = new Board();

            for(int i = 0; i < dim; i++){
                for(int j = 0; j < dim; j++){
                    child.board[i][j] = this.board[i][j];
                }
            }

            child.board[Xlinha][Xcoluna] = child.board[Xlinha -1][Xcoluna];
            child.board[Xlinha -1][Xcoluna] = 0;

            sucs.add(child);
        }

        if (Xlinha < dim -1){

            Board child = new Board();

            for(int i = 0; i < dim; i++){
                for(int j = 0; j < dim; j++){
                    child.board[i][j] = this.board[i][j];
                }
            }

            child.board[Xlinha][Xcoluna] = child.board[Xlinha +1][Xcoluna];
            child.board[Xlinha + 1][Xcoluna] = 0;

            sucs.add(child);
        }

        if (Xcoluna > 0){

            Board child = new Board();

            for(int i = 0; i < dim; i++){
                for(int j = 0; j < dim; j++){
                    child.board[i][j] = this.board[i][j];
                }
            }

            child.board[Xlinha][Xcoluna] = child.board[Xlinha][Xcoluna -1];
            child.board[Xlinha][Xcoluna -1] = 0;

            sucs.add(child);
        }

        if (Xcoluna < dim-1){

            Board child = new Board();

            for(int i = 0; i < dim; i++){
                for(int j = 0; j < dim; j++){
                    child.board[i][j] = this.board[i][j];
                }
            }

            child.board[Xlinha][Xcoluna] = child.board[Xlinha][Xcoluna + 1];
            child.board[Xlinha][Xcoluna +1] = 0;

            sucs.add(child);
        }

        return sucs;
    }

    public boolean isGoal(Ilayout l) {
        // TODO: Compare this configuration with l.

        return (this.equals(l));
    }

    public double getK() {
        // TODO: Return the cost of one move.

        return 1.0;

    }
}
