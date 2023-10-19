package view;

public class CAMsView {

    protected StaffView staffView;

    protected StudentView studentView;

    protected CommMemView commMemView;

    public CAMsView(){
        staffView = new StaffView();
        studentView = new StudentView();
        commMemView = new CommMemView();
    }

    public void startMainView(){

    }
}
