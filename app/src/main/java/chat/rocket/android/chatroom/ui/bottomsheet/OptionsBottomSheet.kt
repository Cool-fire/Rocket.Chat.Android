package chat.rocket.android.chatroom.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import chat.rocket.android.R
import chat.rocket.android.chatroom.adapter.BlockElementOnClicklistener
import chat.rocket.android.chatroom.uimodel.BlockUiModel
import chat.rocket.android.util.extensions.openTabbedUrl
import chat.rocket.core.model.block.elements.OverflowElement
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.item_options.*


class OptionsBottomSheet: BottomSheetDialogFragment(){

    private lateinit var overflowElement: OverflowElement
    private lateinit var data: BlockUiModel
    private lateinit var listener: BlockElementOnClicklistener
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.item_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupList()
    }

    fun addOverFlowItem(overflowElement: OverflowElement, data: BlockUiModel, listener: BlockElementOnClicklistener) {
        this.overflowElement = overflowElement
        this.data = data
        this.listener = listener
    }

    private fun setupList() {
        val list = ArrayList<String>()
        overflowElement.options.forEach { option ->
            list.add(option.text.text)
        }

        val adapter: ArrayAdapter<String> = ArrayAdapter(context, android.R.layout.simple_list_item_1, list)
        list_options.adapter = adapter
        list_options.setOnItemClickListener { parent, view, position, id ->
            onOptionClicked(view, position)
        }
    }

    private fun onOptionClicked(view: View, position: Int) {
        val option = overflowElement.options[position]
        if(option.url != null) {
            option.url?.let { view.openTabbedUrl(it) }
        } else {
            listener.onOverFlowOptionClicked(option, overflowElement, data, view)
        }
    }

    override fun onStart() {
        super.onStart()
        val bottomsheet: FrameLayout = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from(bottomsheet)
    }

    override fun onResume() {
        super.onResume()
        setupList()
    }
}

