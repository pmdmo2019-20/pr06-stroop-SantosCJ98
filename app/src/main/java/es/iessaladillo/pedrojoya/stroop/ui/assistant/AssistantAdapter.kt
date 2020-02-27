package es.iessaladillo.pedrojoya.stroop.ui.assistant

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import es.iessaladillo.pedrojoya.stroop.ui.assistant.Parts.*

val assistant = listOf(OneFragment(), TwoFragment(), ThreeFragment(), FourFragment(), FiveFragment(), SixFragment(), SevenFragment(), EightFragment())

class AssistantAdapter(parent: Fragment): FragmentStateAdapter(parent) {

    override fun getItemCount(): Int = assistant.size

    override fun createFragment(position: Int): Fragment {

        return assistant[position]

    }


}