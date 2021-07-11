using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace interfaceBD
{
	[Serializable()]
    class Overview
    {
		private String _nome;
		private String _dataini;
		private String _numdias;
		private String _numbilhetes;
		private String _promotor;
		private String _banda;
		private String _horaini;
		private String _duracao;

        public string Nome { get => _nome; set => _nome = value; }
        public string Dataini { get => _dataini; set => _dataini = value; }
        public string Numdias { get => _numdias; set => _numdias = value; }
        public string Numbilhetes { get => _numbilhetes; set => _numbilhetes = value; }
        public string Promotor { get => _promotor; set => _promotor = value; }
        public string Banda { get => _banda; set => _banda = value; }
        public string Horaini { get => _horaini; set => _horaini = value; }
        public string Duracao { get => _duracao; set => _duracao = value; }

        public override String ToString()
        {
            return String.Format("{0, -33}{1, -12}{2, -7}{3, -9}{4, -12}{5, -33}{6, -12}{7, -12}", Nome, Dataini, Numdias, Numbilhetes, Promotor, Banda, Horaini, Duracao);
        }

        public static String Fline()
        {
            return String.Format("{0, -33}{1, -12}{2, -7}{3, -9}{4, -12}{5, -33}{6, -12}{7, -12}", "Name", "Data Inicio", "Dias", "Bilhetes", "Promotor", "Banda", "Horaini", "Duracao");
        }
    }
}
