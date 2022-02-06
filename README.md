# LiveDataCallAdapterFactory
based on retrofit, the LiveData returned by the restfull api, can be used to manually re request data via net
（配合retrofit使用，让网络请求返回的livedata可以手动重新发起网络请求）

## use like that:
### 0
root build.gradle: 
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
app build.gradle: 
implementation 'com.github.jj532655203:LiveDataCallAdapter:1.0.0-beta.2'

### 1
    companion object {
        private const val BASE_URL = "https://baseUrl/"
        fun create(): TestApi {
            val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Log.d("API", it + Thread.currentThread().name)
            })
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(HttpUrl.parse(BASE_URL)!!)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(TestApi::class.java)
        }
    }
    
 ### 2
 class MainRepository {

    fun getTest(): RetrofitLiveData<ApiResponse<TestApi.MyResponse>> {
        return TestApi.create().getTest()
    }

    companion object {
        @Volatile
        private var instance: MainRepository? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: MainRepository().also { instance = it }
        }

    }
}

### 3
class MainViewModel internal constructor(mainRepository: MainRepository): ViewModel() {
    val testLiveData: RetrofitLiveData<ApiResponse<TestApi.MyResponse>> = mainRepository.getTest()
}

### 4
class MainActivity : AppCompatActivity() {

    private var mTestRefresh: IApiRefresh? = null

    private val viewModel: MainViewModel by viewModels {
        InjectorUtils.provideMainViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.tv)

        viewModel.testLiveData.observe(this) { result ->
            Log.d("MainActivity", "$result")
            tv.setText(result.toString())
        }

        mTestRefresh = viewModel.testLiveData
        tv.setOnClickListener {
            refreshData()
        }
    }

    private fun refreshData() {
        mTestRefresh?.refresh()
    }

}
