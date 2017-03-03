package backend;

import java.io.Serializable;
//
public class StudentInfo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	byte rank;
    byte oldRank;
    String fag;
    int button;
    String LectureID;

    public StudentInfo(String fag, byte rank, byte oldRank){
        this.fag = fag;
        this.rank = rank;
        this.oldRank = oldRank;
    }

    public byte getRank() {
        return rank;
    }

    public String getFag() {
        return fag;
    }

    public void setRank(byte rank) {

        setOldRank(this.rank);
        this.rank = rank;
    }

    public void setFag(String fag) {
        this.fag = fag;
    }

    public byte getOldRank() {
        return oldRank;
    }

    public void setOldRank(byte oldRank) {
        this.oldRank = oldRank;
    }


	public int getButton() {
		return button;
	}

	public void setButton(int id) {

		this.button = id;
	}
    public void setLectureID(String LID){
        LectureID=LID;
    }
    public String getLectureID(){
        return LectureID;
    }
}