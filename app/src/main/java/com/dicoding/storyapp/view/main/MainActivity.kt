package com.dicoding.storyapp.view.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.storyapp.R
import com.dicoding.storyapp.data.adapter.StoryAdapter
import com.dicoding.storyapp.databinding.ActivityMainBinding
import com.dicoding.storyapp.detail.DetailActivity
import com.dicoding.storyapp.response.Story
import com.dicoding.storyapp.upload.UploadActivity
import com.dicoding.storyapp.view.ViewModelFactory
import com.dicoding.storyapp.view.maps.MapsActivity
import com.dicoding.storyapp.view.welcome.WelcomeActivity


class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: StoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {
                setupAction(user.token)
            }
        }
        setupView()
        supportActionBar?.show()

        adapter = StoryAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnClickCallback(object : StoryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Story) {
                Intent(this@MainActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.NAME, data.name)
                    it.putExtra(DetailActivity.DESC, data.description)
                    it.putExtra(DetailActivity.URL, data.photoUrl)
                    startActivity(it)
                }
            }
        })
        binding.apply {
            rvStory.layoutManager = LinearLayoutManager(this@MainActivity)
            rvStory.setHasFixedSize(true)
            rvStory.adapter = adapter
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.btn_logout -> {
                viewModel.logout()
            }

            R.id.btn_add -> {
                startActivity(Intent(this@MainActivity, UploadActivity::class.java))
            }

            R.id.btn_map -> {
                startActivity(Intent(this@MainActivity, MapsActivity::class.java))
            }

        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    private fun setupAction(token: String) {
        viewModel.story(token).observe(this) { user ->
            if (user != null) {
                binding.progressBar.visibility = View.GONE
                val adapter = StoryAdapter()
                binding.rvStory.layoutManager = LinearLayoutManager(this)
                adapter.submitData(lifecycle,user)
                binding.rvStory.adapter = adapter
            }
        }
    }


}