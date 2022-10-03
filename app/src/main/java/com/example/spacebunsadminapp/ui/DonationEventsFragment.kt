package com.example.spacebunsadminapp.ui

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.spacebunsadminapp.R
import com.example.spacebunsadminapp.data.DonationEventViewModel
import com.example.spacebunsadminapp.databinding.FragmentDonationEventsBinding
import com.example.spacebunsadminapp.util.DonationEventAdapter
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import kotlinx.coroutines.launch

class DonationEventsFragment : Fragment() {
    private lateinit var binding: FragmentDonationEventsBinding
    private val nav by lazy { findNavController() }
    private val vm: DonationEventViewModel by activityViewModels()
    private var progr = 70

    //    private lateinit var adapter: DonationEventAdapter
    lateinit var pieChart: PieChart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDonationEventsBinding.inflate(inflater, container, false)
        updateProgressBar();

//        binding.pbDonations.setOnClickListener { nav.navigate(R.id.donationDetailFragment) }
        binding.fabtnAddDonationEvent.setOnClickListener { nav.navigate(R.id.InsertdonationEventsFragment) }
//        binding.pieChart.setOnClickListener { nav.navigate(R.id.donationsFragment) }

        pieChart = binding.pieChart

        // setting user percent value,
        // setting description as enabled and offset for pie chart
        pieChart.setUsePercentValues(true)
        pieChart.getDescription().setEnabled(false)
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)

        // setting drag for our pie chart
        pieChart.setDragDecelerationFrictionCoef(0.95f)

        // setting hole and hole color for pie chart
        pieChart.setDrawHoleEnabled(true)
        pieChart.setHoleColor(Color.WHITE)

        // setting circle color and alpha
        pieChart.setTransparentCircleColor(Color.WHITE)
        pieChart.setTransparentCircleAlpha(110)

        // setting hole radius
        pieChart.setHoleRadius(58f)
        pieChart.setTransparentCircleRadius(61f)

        // setting center text
        pieChart.setDrawCenterText(true)

        // setting rotation for our pie chart
        pieChart.setRotationAngle(0f)

        // enable rotation of the pieChart by touch
        pieChart.setRotationEnabled(true)
        pieChart.setHighlightPerTapEnabled(true)

        // setting animation for our pie chart
        pieChart.animateY(1400, Easing.EaseInOutQuad)

        // disabling our legend for pie chart
        pieChart.legend.isEnabled = false
        pieChart.setEntryLabelColor(Color.WHITE)
        pieChart.setEntryLabelTextSize(12f)

        // creating array list and
        // adding data to it to display in pie chart
        val entries: ArrayList<PieEntry> = ArrayList()
        entries.add(PieEntry(70f))
        entries.add(PieEntry(20f))
        entries.add(PieEntry(10f))

        // setting pie data set
        val dataSet = PieDataSet(entries, "Mobile OS")

        // setting icons.
        dataSet.setDrawIcons(false)

        // setting slice for pie
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        // add a lot of colors to list
        val colors: ArrayList<Int> = ArrayList()
        colors.add(resources.getColor(R.color.purple_200))
        colors.add(resources.getColor(R.color.yellow))
        colors.add(resources.getColor(R.color.teal_200))

        // setting colors.
        dataSet.colors = colors

        // setting pie data set
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(15f)
        data.setValueTypeface(Typeface.DEFAULT_BOLD)
        data.setValueTextColor(Color.WHITE)
        pieChart.data = data

        // undo all highlights
        pieChart.highlightValues(null)

        // loading chart
        pieChart.invalidate()


        val adapter = DonationEventAdapter() { holder, donationEvent ->
            holder.binding.root.setOnClickListener {
                nav.navigate(
                    R.id.donationsFragment,
                    bundleOf("donationEventId" to donationEvent.donationEventId)
                )
            }
        }
        binding.rvDonations.adapter = adapter
        binding.rvDonations.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        // -----------------------------------------------------------------------------------------

//        // TODO(8): Load categories data into recycler view -> launch block
//        lifecycleScope.launch {
//            val donationEvents = vm.getAll()
//            adapter.submitList(donationEvents)
//            binding.txtDonationCount.text = "${donationEvents.size} Records(s)"
//        }

        // -----------------------------------------------------------------------------------------

        val arrayAdapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnDonationAttributes.adapter = arrayAdapter

        val donations = vm.getDonationAttributes()
        arrayAdapter.add("All")
        arrayAdapter.addAll(donations)

        // TODO: Get all
        vm.getAll().observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.txtDonationCount.text = "${it.size} donation(s)"
        }

        return binding.root
    }

    private fun updateProgressBar() {
//        binding.pbDonations.progress = progr
//        binding.txtDonationProgress.text = progr.toString() + ".00%"
    }
}