package java.com.alumnimanagmentsystem.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {

    public Boolean navigationItemClick;

    public MainActivityViewModel() {
        navigationItemClick = Boolean.FALSE;

    }
}