package com.example.dripdrip.ui.main.fragments

import android.graphics.Color
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.example.dripdrip.R
import com.example.dripdrip.data.entities.User
import com.example.dripdrip.other.EventObserver
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.*

class OthersProfileFragment : ProfileFragment()  {

    private val args: OthersProfileFragmentArgs by navArgs()

    override val uid: String
        get() = args.uid

    private var curUser: User? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObservers()


        btnToggleFollow.setOnClickListener{
            viewModel.toggleFollowForUser(uid)

        }
    }

    private fun subscribeToObservers(){
        viewModel.profileMeta.observe(viewLifecycleOwner, EventObserver {
            if(FirebaseAuth.getInstance().uid!! != uid){
                btnToggleFollow.isVisible = true
                setupToggleFollowButton(it)
                curUser = it
            }

        })
        viewModel.followStatus.observe(viewLifecycleOwner, EventObserver{
            curUser?.isFollowing = it
            setupToggleFollowButton(curUser ?: return@EventObserver)
        })
    }

    private fun setupToggleFollowButton(user: User){
        btnToggleFollow.apply{
            val changeBounds = ChangeBounds().apply {
                duration = 300
                interpolator = OvershootInterpolator()
            }

            val set1 = ConstraintSet()
            val set2 = ConstraintSet()
            set1.clone(requireContext(), R.layout.fragment_profile)
            set2.clone(requireContext(), R.layout.fragment_profile_anim)
            TransitionManager.beginDelayedTransition(clProfile, changeBounds)
            if(user.isFollowing){
                text = requireContext().getString(R.string.unfollow)
                setBackgroundColor(Color.RED)
                setTextColor(Color.WHITE)
                set1.applyTo(clProfile)
            } else {
                text = requireContext().getString(R.string.follow)
                setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorAccent))
                setTextColor(ContextCompat.getColor(requireContext(), R.color.darkBackground))
                set2.applyTo(clProfile)
            }
        }
    }

}