package tech.aditya.theforgettingcurve.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import tech.aditya.theforgettingcurve.R
import tech.aditya.theforgettingcurve.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.rotate_open_anim)}
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.rotate_close_anim)}
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.from_bottom_anim)}
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.to_bottom_anim)}
    
    private var clicked: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )
        clicked = false

        binding.menuFloatingActionButton.setOnClickListener{ onMenuButtonClicked() }
        binding.addFloatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }


        return binding.root
    }

    private fun onMenuButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        clicked = !clicked
    }

    private fun setVisibility(clicked: Boolean) {
        if(!clicked){
            binding.addFloatingActionButton.visibility = View.VISIBLE
            binding.updateFloatingActionButton.visibility = View.INVISIBLE
        }else{
            binding.addFloatingActionButton.visibility = View.INVISIBLE
            binding.updateFloatingActionButton.visibility = View.INVISIBLE
        }

    }

    private fun setAnimation(clicked: Boolean) {
        if(!clicked){
            binding.addFloatingActionButton.startAnimation(fromBottom)
            binding.updateFloatingActionButton.startAnimation(fromBottom)
            binding.menuFloatingActionButton.startAnimation(rotateOpen)
        }else{
            binding.addFloatingActionButton.startAnimation(toBottom)
            binding.updateFloatingActionButton.startAnimation(toBottom)
            binding.menuFloatingActionButton.startAnimation(rotateClose)
        }
    }

    private fun setClickable(clicked: Boolean){
        if(clicked){
            binding.updateFloatingActionButton.isClickable = false
            binding.addFloatingActionButton.isClickable = false
        }else{
            binding.updateFloatingActionButton.isClickable = true
            binding.addFloatingActionButton.isClickable = true
        }
    }


}