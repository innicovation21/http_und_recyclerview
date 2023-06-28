package com.kotlinproject.http_example


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


// durch das Erben von 'RecyclerView.Adapter<>()' machen wir deutlich, dass es sich bei
// MyRecyclerViewAdapter <- um einen RecyclerViewAdapter handelt

// hier müssen wir außerdem unseren ViewHolder für das zu verwendende Item in der RecyclerView
// mitgeben:
// <MyRecyclerViewAdapter.MyViewHolder>
class MyRecyclerViewAdapter: RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>() {

    // Variable für die Liste, welche in der RecyclerView dargestellt werden soll
    private var myPostItemList: List<MyPostItem> = emptyList()

    //region inner class für den ViewHolder
    // in jedem adapter benötigen wir eine inner class, welche
    // dafür zuständig ist, die Elemente aus dem Element abzurufen, welches
    // in der recyclerview verwendet werden soll (in diesem Fall: 'recycler_view_item.xml')
    // diese erbt von 'RecyclerView.ViewHolder()
    // in dessen Constructor ist das View-Element erforderlich, welches wir für unsere
    // RecyclerView verwenden wollen und welche wir unserer ViewHolderClass übergeben.
    // da wir dieses Element in unserer inner class ebenso verwenden,
    // können wir den selben Key benutzen 'itemView'
    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
//endregion

    /**
     * nachdem wir die inner class erstellt und die notwendigen Vererbungen deklariert haben, bekommen
     * wir eine Fehlermeldung, aufgrund fehlender Funktionen, welche überschrieben werden müssen
     * 'implement members' -> öffnet ein neues Fenster mit 3 zur Auswahl stehenden Funktionen
     * --> onCreateViewHolder, getItemCount und onBindViewHolder <--
     * alle 3 markieren und erstellen lassen
     * */

    //region onCreateViewHolder

    // Funktion für das Layout unseres 'recycler_view_item.xml", welches neu erstellt werden muss
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // zur Darstellung der xml-file nutzen wir erneut den Layoutinflater (kennen wir bereits aus unseren MainActivities)
        // dieser benötigt hier den Context, in welchem das Element erzeugt werden soll
        //..
        // da wir uns hier in unserem Adapter befinden und nicht in der Activity,
        // nehmen wir den passenden Key aus unserem primary Constructor 'parent' (parent.context)
        // ..
        // für inflate(resource, root, attachToRoot):
        // resource ist unsere layoutfile für das recyclerviewitem (R.layout.recycler_view_item)
        // root fragt nach einer ViewGroup, in welcher das Item angezeigt werden soll (parent)
        // attachToRoot geben wir false, da wir hier sonst die App direkt abstürzen lassen würden
        // wir wollen das Layout nicht für die RecyclerView verwenden, sondern für das sich darin befindende Item


        val view = LayoutInflater.from(parent.context).inflate(R.layout.mycardview_item, parent, false)
        return MyViewHolder(view)

        //return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false))
    }
    //endregion

    //region onBindViewHolder
    // Funktion zum verbinden von MyPostItem-Variablen mit den TextViews des 'recycler_view_item.xml"
    // sie wird für jedes Element in unserer Liste aufgerufen
    // position: Int wird bei der ersten Ausführung also 0 sein, danach 1, 2, 3 usw
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // MyPostItem.id soll in der TextView mit der id: tvId dargestellt werden + den title entsprechend in tvTitle
        holder.itemView.findViewById<TextView>(R.id.tvId).text = myPostItemList[position].id.toString()
        holder.itemView.findViewById<TextView>(R.id.tvTitle).text = myPostItemList[position].title

        // wenn Bilder mithilfe von Picasso dargestellt werden sollen:
        // 1. Variable für die Url erstellen
        // 2. //Picasso.get().load(<Variable>).into(holder.itemView...) <- entsprechender Syntax zum darstellen des Bildes
    }

    //endregion

    //region getItemCount
    // Funktion zum Festlegen, wieviele Elemente sich in der RecyclerView befinden
    // da wir eine leere Liste festgelegt haben, müssen wir entsprechend später noch
    // eine Funktion hinzufügen, welche unsere eigene Liste dafür einsetzt
    override fun getItemCount(): Int {
        return myPostItemList.size
    }
    //endregion

    

    //region Funktion zum Befüllen der myPostItemList
    //Funktion zum Befüllen der myPostItemList
    fun setList(newList: List<MyPostItem>){
        myPostItemList = newList
        //Hinweis für den Adapter, dass sich der Inhalt der "myPostItemList" geändert hat (von leer -> auf befüllt)
        notifyDataSetChanged()
    }
    //endregion
}
