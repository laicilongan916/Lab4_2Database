package my.edu.tarc.lab4_2database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import java.util.List;

//TODO 6: Create a repository class to manage data query thread

public class UserRepository {
    private UserDAO userDao;
    private LiveData<List<User>> allUsers;

    public UserRepository(Application application) {
        // Create an instance of Database
        AppDatabase db = AppDatabase.getDatabase(application);
        // Create an instance of DAO
        userDao = db.userDao();
        // Retrieve all user records
        allUsers = userDao.loadAllUsers();
    }
    
    LiveData<List<User>> getAllUsers(){
        return allUsers;
    }
    
    public void insertUser(User user){
        new insertAsyncTask(userDao).execute(user);
    }

    // INNER CLASS OF AN ASYNCTASK
    //<Param, Progress, Results>
    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDAO userDao;

        public insertAsyncTask(UserDAO userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insertUser(users[0]);
            return null;
        }
    }
}
