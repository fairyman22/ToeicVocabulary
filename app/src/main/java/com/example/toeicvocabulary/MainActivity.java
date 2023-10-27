package com.example.toeicvocabulary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.toeicvocabulary.Database.DatabaseHelper;
import com.example.toeicvocabulary.Model.Question;
import com.google.android.material.bottomnavigation.BottomNavigationView;



public class MainActivity extends AppCompatActivity {
    // Định nghĩa các hằng số cho các ID
    private static final int NAV_HOME = R.id.nav_home;
    private static final int NAV_SEARCH = R.id.nav_search;
    private static final int NAV_MY_WORDS = R.id.nav_my_words;
    private static final int NAV_SETTINGS = R.id.nav_settings;
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    private static final int HOME_FRAGMENT = 0;
    private static final int SEARCH_FRAGMENT = 1;
    private static final int MY_WORDS_FRAGMENT = 2;
    private static final int SETTINGS_FRAGMENT = 3;
    private int currentFragment = HOME_FRAGMENT;
    private DatabaseHelper database_hp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database_hp = new DatabaseHelper(this, "ToeicVocab.sqlite", null, 1);
        database_hp.QueryData("CREATE TABLE IF NOT EXISTS ListWords(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(200), type VARCHAR(200), mean VARCHAR(200))");
        database_hp.QueryData("CREATE TABLE IF NOT EXISTS Wordss(Id INTEGER PRIMARY KEY AUTOINCREMENT, word VARCHAR(200), listid VARCHAR(200), mean VARCHAR(200), pronunc VARCHAR(200), sound VARCHAR(200), image VARCHAR(200), type VARCHAR(200))");
        database_hp.QueryData("CREATE TABLE IF NOT EXISTS Question(Id INTEGER PRIMARY KEY AUTOINCREMENT, question VARCHAR(200), linksound VARCHAR(200), a VARCHAR(200), b VARCHAR(200), c VARCHAR(200), d VARCHAR(200), answer VARCHAR(200), idList VARCHAR(200), type VARCHAR(200))");
        database_hp.QueryData("CREATE TABLE IF NOT EXISTS User(Id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR(200), password VARCHAR(200), name VARCHAR(200), old VARCHAR(200), sdt VARCHAR(200), email VARCHAR(200))");

////        Question
////        1 - contract
//        database_hp.QueryData("INSERT INTO Question VALUES(1,'Agreement','https://audio.jukehost.co.uk/WzsDAJoIhwlZ5NlvP5xIsw7vxG0WPTtc','Hợp đồng' , 'Ký kết', 'Hiệp định', 'Đảm bảo', 'Hiệp định', '1','mean')");
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'Assurance','https://audio.jukehost.co.uk/8vLejTXATXaxony7Mg6bAg1HDCBfjBtz','Sự hủy bỏ' , 'Ký kết', 'Hợp đồng', 'Đảm bảo', 'Đảm bảo', '1','mean')");
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'cancellation','https://audio.jukehost.co.uk/WzsDAJoIhwlZ5NlvP5xIsw7vxG0WPTtc','Sự hủy bỏ' , 'Ký kết', 'Hiệp định', 'Hợp đồng', 'Sự hủy bỏ', '1','mean')");
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'Determine','https://audio.jukehost.co.uk/z9zqoOS2UcmRqJOePn4BsZGOJkaKCELj','Quyết tâm' , 'Ký kết', 'Đảm bảo', 'Đảm bảo', 'Quyết tâm', '1','mean')");
//          database_hp.QueryData("INSERT INTO Question VALUES(null,'engage','https://audio.jukehost.co.uk/sWWwGsp4pOy4rhixVqaguMbrhqXOFxvr','Bắt đầu' , 'Tuyển dụng', 'Hiệp định', 'Kết thúc', 'Tuyển dụng', '1','mean')");
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'establish','https://audio.jukehost.co.uk/XnSLBvE3wYtaUvIES7xMqf2mncxU05RS','Thuê' , 'Tuyển dụng', 'Thành lập', 'Kết thúc', 'Thành lập', '1','mean')");
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'obligate','https://audio.jukehost.co.uk/HMfqrzoweeo30qvXQ0oyqTlpEseapaLX','Bắt buộc' , 'Tự do', 'Thành lập', 'Kết thúc', 'Bắt buộc', '1','mean')");
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'provision','https://audio.jukehost.co.uk/6iOP7Ncwi3NVI2sVAHvmvM2zCSvUKuNZ','Thành lập' , 'Tự do', 'Sự cung cấp', 'Kết thúc', 'Sự cung cấp', '1','mean')");
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'resolve','https://audio.jukehost.co.uk/zBRec5yRsKe3WXRaKfUKx6LDi4KqAISQ','Bắt buộc' , 'Nghỉ hưu', 'Bắt đầu', 'Giải quyết', 'Giải quyết', '1','mean')");
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'specific','https://audio.jukehost.co.uk/iephdF1zgmXCGz3399rjC4xdPleaKOzJ','Cụ thể' , 'Nghỉ hưu', 'Bắt đầu', 'Thành lập', 'Cụ thể', '1','mean')");
////        2 - marketing
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'attract','https://audio.jukehost.co.uk/0ngJA697YjPepYoISWmtp87feXX6Z9Nm','Thu hút' , 'Tuyển dụng', 'Chiến lược', 'Kết thúc', 'Thu hút', '2','mean')");
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'competition','https://audio.jukehost.co.uk/oqAviLLk40hYNN0AmCyzTDf69oMrlFBm','Thuê' , 'Cuộc thi', 'Tren', 'Khách hàng', 'Cuộc thi', '2','mean')");
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'consume','https://audio.jukehost.co.uk/EepCSBjSJgnogUvZkk387iBJxIRYWEvJ','Cuộc thi' , 'Thu hút', 'Tiêu thụ', 'Kết thúc', 'Tiêu thụ', '2','mean')");
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'convince','https://audio.jukehost.co.uk/CozR8zxgkZukjRt3cqW6GDvr2mJTNVW9','Thuyết trình' , 'Tuyển dụng', 'Thuyết phục', 'Báo cáo', 'Thuyết phục', '2','mean')");
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'fad','https://audio.jukehost.co.uk/XnSLBvE3wYtaUvIES7xMqf2mncxU05RS','Thịnh hành' , 'Xu hướng ngắn', 'Thành lập', 'Thu hút', 'Xu hướng ngắn', '2','mean')");
//        //3 - warranties
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'accommodate','https://audio.jukehost.co.uk/4ZxoE9qtl7D0mQ0QI8RV29CAnzTqgFuu','Chứa, cung cấp' , 'Bảo vệ', 'Hết hạn', 'Kết thúc', 'Chứa, cung cấp', '3','mean')");
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'consequence','https://audio.jukehost.co.uk/HaG2aghjx10qlOsABeAHl33agv0wlBJo','Chứa, cung cấp' , 'Kết quả', 'Hết hạn', 'Kết thúc', 'Kết quả', '3','mean')");
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'consider','https://audio.jukehost.co.uk/VLIQU0z6QApWcsjzGnp3nOhI2Kc3HRCB','Suy nghĩ' , 'Bảo vệ', 'Hết hạn', 'Lợi nhuận', 'Suy nghĩ', '3','mean')");
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'expiration','https://audio.jukehost.co.uk/IoOqjbw9HMkL8HRHoropIMiqkMSqxEJ1','Chứa, cung cấp' , 'Bảo vệ', 'Hết hạn', 'Kết thúc', 'Hết hạn', '3','mean')");
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'accommodate','https://audio.jukehost.co.uk/Eey9JiqwV7wPW4Q4MBbWPOY4nnDujJpS','Chứa, cung cấp' , 'Bảo vệ', 'Hết hạn', 'Bao hàm, ngu ý', 'Bao hàm, ngu ý', '3','mean')");
//        //4 - conferences
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'accommodate','https://audio.jukehost.co.uk/4ZxoE9qtl7D0mQ0QI8RV29CAnzTqgFuu','Chứa, cung cấp' , 'Bảo vệ', 'Hết hạn', 'Kết thúc', 'Chứa, cung cấp', '4','mean')");
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'arraignment','https://audio.jukehost.co.uk/e7cSfRXqltIHExzHq7xBocoRHLAkvdcg','Suy diễn' , 'Bảo vệ', 'Hết hạn', 'Buộc tội', 'Buộc tội', '4','mean')");
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'association','https://audio.jukehost.co.uk/25YpLhUgYHh5UvxU6MUzdEtaRpKH6IVO','Ký kết' , 'Bảo vệ', 'Sự kết hợp', 'Kết thúc', 'Sự kết hợp', '4','mean')");
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'attend','https://audio.jukehost.co.uk/UjF31JLTrRextJDhduLGFGpGmbQExYUJ','Tham gia' , 'Sa thải', 'Hết hạn', 'Đuổi việc', 'Tham gia', '4','mean')");
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'overcrowded','https://audio.jukehost.co.uk/TZTf5aQ7dZ8UsyLbQsmMzKcTvdevbJwC','Chứa, cung cấp' , 'Bảo vệ', 'Hết hạn', 'Đông đúc', 'Đông đúc', '4','mean')");
//        //5 - computer
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'access','https://audio.jukehost.co.uk/Y4GtFiUtWPAWwgXtdVmt5ZKpQdq7tje2','Chấp thuận' , 'Đĩa', 'màn hình', 'Đuổi việc', 'Chấp thuận', '5','mean')");
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'allocate','https://audio.jukehost.co.uk/v4nwJZ9DuxIy11IZI13IW0XFHVQcw0IH','Chỉ định' , 'Sa thải', 'Hết hạn', 'Đuổi việc', 'Chỉ định', '5','mean')");
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'duplicate','https://audio.jukehost.co.uk/XDa1XpO8Slf66GcNcepfEOy4K1wAC62Q','Đĩa' , 'Sa thải', 'Hết hạn', 'Nhân bản', 'Nhân bản', '5','mean')");
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'ignore','https://audio.jukehost.co.uk/thWVsN05XwaPq0Z1HWtmkFmYOew3Uixr','Làm việc' , 'Chu trình', 'Phớt lờ, bỏ qua', 'Đuổi việc', 'Phớt lờ, bỏ qua', '5','mean')");
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'warn','https://audio.jukehost.co.uk/UjF31JLTrRextJDhduLGFGpGmbQExYUJ','Cảnh tỉnh' , 'Cảnh báo', 'Hết hạn', 'Đuổi việc', 'Cảnh báo', '5','mean')");
//        //6 - electronic
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'disk','https://audio.jukehost.co.uk/sPVJ0SebiBtcHB3ZV6AJn5Lyn9sazSD9','Đĩa' , 'Sa thải', 'Hết hạn', 'Nhân bản', 'Đĩa', '6','mean')");
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'facilitate','https://audio.jukehost.co.uk/V52EnOdQHa0rsy9HDnrr37mtQTek9K5o','Đĩa' , 'Khó khăn', 'Tạo điều kiện', 'Nhân bản', 'Tạo điều kiện', '6','mean')");
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'revolution','https://audio.jukehost.co.uk/veA9ZuDcBDC3O7wqtVc9noB5B7P9uE8u','Đĩa' , 'Gây khó khăn', 'Hết hạn', 'Cuộc cách mạng', 'Cuộc cách mạng', '6','mean')");
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'software','https://audio.jukehost.co.uk/ny7W7aGBXfCt5q6fBGMjaAP0sJNfW1qq','Phần mềm' , 'Sa thải', 'Phần cứng', 'Vi rút', 'Phần mềm', '6','mean')");
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'store','https://audio.jukehost.co.uk/rx3O9LydiSOgWTv5sU0N1dkuTSm2waZj','Đĩa' , 'Phần cứng', 'Cửa hàng, lưu trữ', 'Nhân', 'Cửa hàng, lưu trữ', '6','mean')");
//        //7 - recruit
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'abundant','https://audio.jukehost.co.uk/XDa1XpO8Slf66GcNcepfEOy4K1wAC62Q','Dồi dào' , 'Khan hiếm', 'Hết hạn', 'Có sẵn', 'Dồi dào', '7','mean')");
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'accomplishment','https://audio.jukehost.co.uk/XDa1XpO8Slf66GcNcepfEOy4K1wAC62Q','thành trì' , 'Sa thải', 'Hết hạn', 'Thành tựu', 'Thành tựu', '7','mean')");
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'candidate','https://audio.jukehost.co.uk/XDa1XpO8Slf66GcNcepfEOy4K1wAC62Q','Nhà tuyển dụng' , 'Sa thải', 'ứng viên', 'Người quan sát', 'ứng viên', '7','mean')");
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'commensurate','https://audio.jukehost.co.uk/XDa1XpO8Slf66GcNcepfEOy4K1wAC62Q','Thất vọng' , 'Sa thải', 'Hết hạn', 'Tương xứng', 'Tương xứng', '7','mean')");
//        database_hp.QueryData("INSERT INTO Question VALUES(null,'retire','https://audio.jukehost.co.uk/XDa1XpO8Slf66GcNcepfEOy4K1wAC62Q','Nghỉ hưu' , 'Sa thải', 'Hết hạn', 'Xin nghỉ', 'Nghỉ hưu', '7','mean')");
//
//
//
////        ListWord
//
//
//        database_hp.QueryData("INSERT INTO ListWords VALUES(null,'Contract','system','Hợp đồng')");
//        database_hp.QueryData("INSERT INTO ListWords VALUES(null,'Marketing','system','Tiếp thị')");
//        database_hp.QueryData("INSERT INTO ListWords VALUES(null,'Warranties','system','Bảo hiểm')");
//        database_hp.QueryData("INSERT INTO ListWords VALUES(null,'Conferences','system','Hội nghị')");
//        database_hp.QueryData("INSERT INTO ListWords VALUES(null,'Computer','system','Máy tính')");
//        database_hp.QueryData("INSERT INTO ListWords VALUES(null,'Electronic','system','Thiết bị điện tử')");
//        database_hp.QueryData("INSERT INTO ListWords VALUES(null,'Recruiting','system','Tuyển dụng')");
//
////        Word
//
//        database_hp.QueryData("INSERT INTO Wordss VALUES(null,'abide by','1','Tôn trọng, tuân theo','/əˈbaɪd/','https://audio.jukehost.co.uk/USuH3L2JZOqK167EtjiHllm4BqcFZNsJ','https://i0.wp.com/prosperoenglish.com/wp-content/uploads/2020/06/hammer-719066_1920-1.jpg?resize=481%2C360&ssl=1','system')");
//        database_hp.QueryData("INSERT INTO Wordss VALUES(null,'agreement','1','Hiệp định','əˈɡriː.mənt','https://audio.jukehost.co.uk/WzsDAJoIhwlZ5NlvP5xIsw7vxG0WPTtc','https://vakilsearch.com/blog/wp-content/uploads/2022/05/master-service-agreement-vs-statement-of-work.jpg','system')");
//        database_hp.QueryData("INSERT INTO Wordss VALUES(null,'assurance','1','sự đảm bảo','/əˈʃʊr.əns/','https://audio.jukehost.co.uk/8vLejTXATXaxony7Mg6bAg1HDCBfjBtz','https://thefinancialcrimenews.com/wp-content/uploads/2019/06/assurance-1.jpg','system')");
//        database_hp.QueryData("INSERT INTO Wordss VALUES(null,'cancellation','1','hủy bỏ một khế ước, sự bải bỏ, sự hủy bỏ','/ˌkæn.səlˈeɪ.ʃən/','https://audio.jukehost.co.uk/WzsDAJoIhwlZ5NlvP5xIsw7vxG0WPTtc','https://images.sampletemplates.com/wp-content/uploads/2019/05/Contract-Cancellation-Letter-Samples.png','system')");
//        database_hp.QueryData("INSERT INTO Wordss VALUES(null,'determine','1','quyết tâm','/dɪˈtɝː.mɪn/','https://audio.jukehost.co.uk/z9zqoOS2UcmRqJOePn4BsZGOJkaKCELj','https://media.dolenglish.vn/PUBLIC/MEDIA/cba74ff1-1ef9-41bd-9c68-979c3b14c686.jpg','system')");



        fragmentManager = getSupportFragmentManager();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        // Xác định fragment ban đầu để hiển thị
        int initialFragment = HOME_FRAGMENT;
        if (savedInstanceState != null) {
            // Nếu có dữ liệu trong savedInstanceState, sử dụng nó để xác định fragment hiện tại.
            initialFragment = savedInstanceState.getInt("CURRENT_FRAGMENT");
        }
        // Mặc định, hiển thị fragment Home
        loadFragment(initialFragment);
        setupBottomNavigationView();
    }

    private void setupBottomNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == NAV_HOME) {
                    // Xử lý khi chọn tab Home
                    loadFragment(HOME_FRAGMENT);
                    return true;
                } else if (itemId == NAV_SEARCH) {
                    // Xử lý khi chọn tab Search
                    loadFragment(SEARCH_FRAGMENT);
                    return true;
                }else if (itemId == NAV_MY_WORDS) {
                    // Xử lý khi chọn tab Search
                    loadFragment(MY_WORDS_FRAGMENT);
                    return true;
                } else if (itemId == NAV_SETTINGS) {
                    loadFragment(SETTINGS_FRAGMENT);
                    return true;
                } else {
                    // Xử lý khi không khớp với bất kỳ trường hợp nào
                    return false;
                }

            }
        });
    }


    private void loadFragment(int fragmentIndex) {
        Fragment fragment = null;

        switch (fragmentIndex) {
            case HOME_FRAGMENT:
                fragment = new HomeFragment();
                break;
            case SEARCH_FRAGMENT:
                fragment = new SearchFragment();
                break;
            case MY_WORDS_FRAGMENT:
                fragment = new UserListFragment();
                break;
            case SETTINGS_FRAGMENT:
                fragment = new SettingFragment();
                break;
        }

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Lưu trạng thái hiện tại của fragment
        outState.putInt("CURRENT_FRAGMENT", currentFragment);
    }
}