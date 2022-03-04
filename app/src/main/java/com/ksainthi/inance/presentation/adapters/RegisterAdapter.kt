import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ksainthi.inance.presentation.fragments.FragmentRegisterStepOne
import com.ksainthi.inance.presentation.fragments.FragmentRegisterStepTwo


class RegisterAdapter(fa: Fragment) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FragmentRegisterStepOne()
            else -> FragmentRegisterStepTwo()
        }
    }
}
