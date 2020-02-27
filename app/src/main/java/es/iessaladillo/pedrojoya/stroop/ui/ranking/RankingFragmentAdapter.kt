package es.iessaladillo.pedrojoya.stroop.ui.Ranking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.data.dao.PlayerDao
import es.iessaladillo.pedrojoya.stroop.data.entity.Ranking
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_ranking_item.*

typealias OnItemClickListener4 = (position: Int) -> Unit

class RankingFragmentAdapter() : RecyclerView.Adapter<RankingFragmentAdapter.ViewHolder>() {

    private var data: List<Ranking> = emptyList()

    private var OnItemClickListener4: OnItemClickListener4? = null

    fun setOnItemClickListener4(OnItemClickListener4: OnItemClickListener4) {

        this.OnItemClickListener4 = OnItemClickListener4

    }

    fun getItem(position: Int) = data.get(position)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val itemView = layoutInflater.inflate(R.layout.fragment_ranking_item, parent, false)

        return ViewHolder(itemView, OnItemClickListener4)

    }

    fun submitList(newData: List<Ranking>) {

        data = newData

        notifyDataSetChanged()

    }

    init {

        setHasStableIds(true);

    }


    override fun getItemId(position: Int): Long = data[position].idRanking

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class ViewHolder(override val containerView: View,  OnItemClickListener4: OnItemClickListener4?) :

        RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {

            itemView.setOnClickListener {

                val position = adapterPosition

                if (position != RecyclerView.NO_POSITION) {

                    OnItemClickListener4?.invoke(position)

                }

            }

        }

        fun bind(ranking: Ranking) {

            imgPlayerR.setImageResource(ranking.jugadorob.avatar)

            lblNombreR.text = ranking.jugadorob.nombre

            lblMode.text = "Game Mode: " + ranking.modo_juego

            lblMinutes.text = "Minutes: " + ranking.minutos

            lblCorrect.text = "Correct: " + ranking.correctas

            lblWords.text = "Words: " + ranking.palabras

            lblScore.text = ranking.puntos.toString()

        }


    }

}