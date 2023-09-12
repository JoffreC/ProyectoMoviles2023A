package com.example.sistemasolarcrud

class SistemaSolarDATA {
    companion object {
        private val sistemaSolarList = arrayListOf<SistemaSolar>()
        var id = 0
        fun createSistemaSolar(
            name: String,
            numPlanetas: Int,
            planetas: ArrayList<Planeta>,
            extension: Double,
            estrellaCentral: Char
        ) {
            val sistema = SistemaSolar(name, numPlanetas, planetas, extension, estrellaCentral)
            id++
            sistemaSolarList.add(sistema)
        }

        fun createPlaneta(
            name: String,
            numLunas: Int,
            habitado: Boolean,
            diametro: Double,
            clasificacion: Char
        ): Planeta {
            val planeta = Planeta(name, numLunas, habitado, diametro, clasificacion)
            return planeta
        }

        fun deletePlaneta(ss: Int, id: Int): Boolean {
            var planeta = sistemaSolarList[ss]?.getPlanetas()?.get(id - 1)

            if (planeta != null) {
                sistemaSolarList[ss]?.getPlanetas()?.remove(planeta)
                return true
            } else {
                return false
            }
        }

        fun getSistemas(): ArrayList<SistemaSolar> {
            var s = ArrayList<Planeta>()
            s.add(Planeta("P1",2,true,20000.0,'C'))
            s.add(Planeta("P2",3,false,200000.2,'H'))
            val s1 = SistemaSolar("S1",2,s,3000.2,'E')
            sistemaSolarList.add(s1)
            return sistemaSolarList
        }

        /*
                fun getSistema(id: Int): SistemaSolar? {
                    return sistemaSolarList[id]
                }*/
    }
}
/*
        fun deleteSistemaSolar(id: Int): Boolean {
            val sistemaSolar = getSistema(id)
            if (sistemaSolar != null) {
                sistemaSolarList.remove(id)
                return true
            } else {
                return false
            }
        }

        fun updateNombreSS(idSS: Int, nombre: String): Boolean {
            var sistema = sistemaSolarList[idSS]
            if (sistema != null) {
                sistema.setNombre(nombre)
                return true
            } else {
                return false
            }
        }

        fun updateExtensionSS(idSS: Int, extension: Double): Boolean {
            var sistema = sistemaSolarList[idSS]
            if (sistema != null) {
                sistema?.setExtension(extension)
                return true
            } else {
                return false
            }
        }

        fun updateFechaSS(idSS: Int, atrib: String): Boolean {
            var estrellaCentral = atrib.toString().toCharArray()[0]
            var sistema = sistemaSolarList[idSS]
            if (sistema != null) {
                sistema?.setExtrellaCentral(estrellaCentral)
                return true
            } else {
                return false
            }
        }

        fun updateNombrePl(numSS: Int, numPl: Int, nombre: String): Boolean {
            var planeta = sistemaSolarList.get(numSS)?.getPlanetas()?.get(numPl)
            if (planeta != null) {
                planeta.setNombre(nombre)
                return true
            } else {
                return false
            }
        }

        fun updateNumLPl(numSS: Int, numPl: Int, numL: Int): Boolean {
            var planeta = sistemaSolarList.get(numSS)?.getPlanetas()?.get(numPl - 1)
            if (planeta != null) {
                planeta.setNumeroLunas(numL)
                return true
            } else {
                return false
            }
        }

        fun updateDiametroPl(numSS: Int, numPl: Int, diametro: Double): Boolean {
            var planeta = sistemaSolarList.get(numSS)?.getPlanetas()?.get(numPl)
            if (planeta != null) {
                planeta.setDiametro(diametro)
                return true
            } else {
                return false
            }
        }

        fun updateExistenciaVidaPl(numSS: Int, numPl: Int): Boolean {
            var planeta = sistemaSolarList.get(numSS)?.getPlanetas()?.get(numPl)
            if (planeta != null) {
                if (planeta.getHabitado()) {
                    planeta.setHabitado(false)
                } else {
                    planeta.setHabitado(true)
                }
                return true
            } else {
                return false
            }
        }

        fun updateClasificacionPl(numSS: Int, numPl: Int, clasificacion: Char): Boolean {
            var planeta = sistemaSolarList.get(numSS)?.getPlanetas()?.get(numPl)
            if (planeta != null) {
                planeta.setClasificacion(clasificacion)
                return true
            } else {
                return false
            }
        }
        /*
    fun guardar() {
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .create()
        val json = gson.toJson(this)
        FileWriter("src/main/sistemaSolar.json").use { writer ->
            writer.write(json)
        }
    }
    }*/
*/