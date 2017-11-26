package com.example.android.mobileprogrammingassignment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.mobileprogrammingassignment.api.UserListApi;
import com.example.android.mobileprogrammingassignment.api.UserListService;
import com.example.android.mobileprogrammingassignment.model.Result;
import com.example.android.mobileprogrammingassignment.model.User;
import com.example.android.mobileprogrammingassignment.utils.PaginationAdapterCallback;
import com.example.android.mobileprogrammingassignment.utils.PaginationScrollListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PaginationAdapterCallback {

    private static final String TAG = "MainActivity";

    RecyclerView rv;
    PaginationAdapter parentAdapter;

    // Assign the
    private static final int PAGE_START = 0;
    private int currentPage = PAGE_START;
    private boolean isLoading = false;
    private boolean isLastPage = false;

    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private int TOTAL_PAGES = 100;
    private int LIMIT = 10;

    private UserListService userListService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the recycler view
        rv = (RecyclerView) findViewById(R.id.main_recycler);

        // Set the animator for our main parent recycler view
        rv.setItemAnimator(new DefaultItemAnimator());

        // Create a new adapter for parent recycler view
        parentAdapter = new PaginationAdapter(this);
        rv.setAdapter(parentAdapter);

        // TODO Try to set predictive animation for gridlayout manager to false



        // Add scroll listener to recycler view
        rv.addOnScrollListener(new PaginationScrollListener((LinearLayoutManager)rv.getLayoutManager()) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += LIMIT;

                loadNextPage();
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });


        //init service and load data
        userListService = UserListApi.getClient().create(UserListService.class);

        loadFirstPage();

    }

    private void loadFirstPage() {
        Log.d(TAG, "loadFirstPage: ");

        callGetAllUsersApi().enqueue(new Callback<Result>() {

            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                // Got data. Send it to adapter
                List<User> results = fetchResults(response);
                parentAdapter.addAll(results);

                if(currentPage == TOTAL_PAGES) {
                    isLastPage = true;
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    private void loadNextPage() {
        Log.d(TAG, "loadNextPage: " + currentPage);

        callGetAllUsersApi().enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                isLoading = false;

                List<User> results = fetchResults(response);
                parentAdapter.addAll(results);

                if (currentPage == TOTAL_PAGES) {
                    isLastPage = true;
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /**
     * @param response extracts List<{@link User }></{@link> from response
     * @return
     */
    private List<User> fetchResults(Response<Result> response) {
        Result data = response.body();
        return data.getData().getUsers();
    }



    /**
     * Performs a Retrofit call to the API.
     * Same API call for Pagination.
     * As {@link #currentPage} will be incremented automatically
     * by @{@link PaginationScrollListener} to load next page.
     */
    private Call<Result> callGetAllUsersApi() {
        return userListService.getCallGetAllUsers(
                currentPage,
                LIMIT
        );
    }
}
