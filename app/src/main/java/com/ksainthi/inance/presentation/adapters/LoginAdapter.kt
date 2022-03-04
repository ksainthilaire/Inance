import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ksainthi.inance.presentation.fragments.FragmentLoginForgotForm
import com.ksainthi.inance.presentation.fragments.FragmentLoginForm


class LoginAdapter(fa: Fragment) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FragmentLoginForm()
            else -> FragmentLoginForgotForm()
        }
    }
}
