package com.simmorsal.smartnode.models

import com.simmorsal.smartnode.R

class ModelMainItems {
    var id = 0
    var title = ""
    var image = 0
    var works = false

    companion object {
        fun getLamps(): List<ModelMainItems> {
            val data = ArrayList<ModelMainItems>()

            run {
                val o = ModelMainItems()
                o.title = "آشپزخانه"
                o.image = R.drawable.lamp_kitchen
                data.add(o)
            }

            run {
                val o = ModelMainItems()
                o.title = "اتاق خواب"
                o.image = R.drawable.lamp_bedroom
                data.add(o)
            }

            run {
                val o = ModelMainItems()
                o.title = "میز مطالعه"
                o.image = R.drawable.lamp_desk
                data.add(o)
            }

            run {
                val o = ModelMainItems()
                o.title = "حمام"
                o.image = R.drawable.lamp_bathroom
                data.add(o)
            }

            return data
        }


        fun getOutlets(): List<ModelMainItems> {
            val data = ArrayList<ModelMainItems>()

            run {
                val o = ModelMainItems()
                o.id = 0
                o.title = "یخچال"
                o.image = R.drawable.outlet_1
                o.works = true
                data.add(o)
            }

            run {
                val o = ModelMainItems()
                o.id = 0
                o.title = "تلویزیون"
                o.image = R.drawable.outlet_2
                o.works = true
                data.add(o)
            }

            run {
                val o = ModelMainItems()
                o.id = 0
                o.title = "کنار میز مطالعه"
                o.image = R.drawable.outlet_3
                o.works = true
                data.add(o)
            }

            run {
                val o = ModelMainItems()
                o.id = 0
                o.title = "بالکن"
                o.image = R.drawable.outlet_4
                o.works = true
                data.add(o)
            }

            return data
        }


        fun getSwitches(): List<ModelMainItems> {
            val data = ArrayList<ModelMainItems>()

            run {
                val o = ModelMainItems()
                o.id = 0
                o.title = "کولر"
                o.image = R.drawable.switch_4
                data.add(o)
            }

            run {
                val o = ModelMainItems()
                o.id = 0
                o.title = "بالکن"
                o.image = R.drawable.switch_2
                data.add(o)
            }

            run {
                val o = ModelMainItems()
                o.id = 0
                o.title = "حیاط"
                o.image = R.drawable.switch_3
                data.add(o)
            }

            run {
                val o = ModelMainItems()
                o.id = 0
                o.title = "انباری"
                o.image = R.drawable.switch_1
                data.add(o)
            }

            return data
        }
    }


}