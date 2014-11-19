
public class Main {

    public static int getRandomNumber(int N){
        return StdRandom.uniform(N)+1;
    }

    public static boolean openRandomSite(Percolation p, int N){
        int i = getRandomNumber(N);
        int j = getRandomNumber(N);

        if(!p.isOpen(i,j)){
            p.open(i,j);
            return true;
        } else
        return openRandomSite(p, N);
    }

    public static void openSitesUntilPercolates(Percolation p, int N){
        while(!p.percolates()) {
            openRandomSite(p,N);
           printGrid(p,N);
        }
    }

    public static void printGrid( Percolation p, int grid) {

        System.out.println("---------- Grid ----------");
        int result;
        for (int i = 1; i <= grid; i++) {
            for (int j = 1; j <= grid ; j++) {
                if(p.isOpen(i,j)){
                    result = 1;
                } else {
                    result = 0;
                }
                System.out.print(result + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int N = 4;
        Percolation p = new Percolation(N);
        openSitesUntilPercolates(p, N);
    }
}
