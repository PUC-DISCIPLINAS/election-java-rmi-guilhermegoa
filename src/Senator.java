public class Senator {
    String nunber;
    String name;
    String political_party;
    int votes = 0;

    Senator(String nunber, String name, String political_party) {
        this.nunber = nunber;
        this.name = name;
        this.political_party = political_party;
    }

    public void vote() {
        this.votes++;
    }

    public String getVoted(){
        return "Votes: " + this.votes;
    }

    @Override
    public String toString() {
        return "Senator{" +
                "nunber='" + nunber + '\'' +
                ", name='" + name + '\'' +
                ", political_party='" + political_party + '\'' +
                ", votes=" + votes +
                '}';
    }
}
