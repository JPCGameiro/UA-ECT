/**
 * \file 
 * \brief The \b sofs20 exception devtools
 * \author Artur Pereira - 2016-2020
 */
#ifndef __SOFS20_EXCEPTION__
#define __SOFS20_EXCEPTION__

#include <exception>

namespace sofs20
{

    /** @{ */

    /**
     * \brief The \b sofs20 exception class
     * \ingroup exception
     */
    class SOException:public std::exception
    {
      public:
        int en;                 ///< (system) error number
        const char *func;       ///< name of function that has thrown the exception
        char msg[100];          ///< buffer to store the exception message

        /**
         * \brief the constructor
         * \param _en (system) error number 
         * \param _func name of function throwing the exception
         */
         SOException(int _en, const char *_func);

        /**
         * \brief default exception message
         * \return pointer to exception message
         */
        const char *what() const throw();
    };

    /** @} */

};

#endif /* __SOFS20_EXCEPTION__ */
