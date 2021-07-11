using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Eventos
{
    [Serializable()]
    public class Evento
    {
		private String _id;
		private String _name;
		private String _numBilhetes;
		private String _numdias;
		private String _dataini;
		private String _datafim;
		private String _cc_promotor;
		private String _dataProposta;
		private String _cc_stageManager;

        public string Id { get => _id; set => _id = value; }
        public string Name { get => _name; set => _name = value; }
        public string NumBilhetes { get => _numBilhetes; set => _numBilhetes = value; }
        public string Numdias { get => _numdias; set => _numdias = value; }
        public string Dataini { get => _dataini; set => _dataini = value; }
        public string Datafim { get => _datafim; set => _datafim = value; }
        public string Cc_promotor { get => _cc_promotor; set => _cc_promotor = value; }
        public string DataProposta { get => _dataProposta; set => _dataProposta = value; }
        public string Cc_stageManager { get => _cc_stageManager; set => _cc_stageManager = value; }

        public override String ToString()
        {
            return String.Format("{0, -5}{1, -33}{2, -11}{3, -7}{4, -12}{5, -12}{6, -12}{7, -12}{8, -14}", this._id, this._name, this._numBilhetes, this._numdias, this._dataini, this._datafim, this._cc_promotor, this._dataProposta, this._cc_stageManager);
        }

        public static String Fline()
        {
            return String.Format("{0, -5}{1, -33}{2, -11}{3, -7}{4, -12}{5, -12}{6, -12}{7, -12}{8, -14}", "Id", "Name", "Bilhetes", "Dias", "Inicio", "Fim", "Promotor", "Proposta", "Stage Manager");
        }
    }
}
