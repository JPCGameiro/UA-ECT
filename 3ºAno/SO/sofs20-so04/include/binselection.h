/**
 *  \file 
 *  \brief The bin IDs selection toolkit.
 *
 *  This toolkit provides a simple way to select what binary version of
 *  the functions to use in a given moment.
 *  The selection is done in run time.
 *  The IDs of the functions are the same used by the probing system.
 *
 *  \author Artur Pereira - 2019-2020
 *
 *  \remarks In case an error occurs, every function throws an error code (an int)
 */

#ifndef __SOFS20_BIN_SELECTION__
#define __SOFS20_BIN_SELECTION__

#include <inttypes.h>

namespace sofs20
{

    /** 
     * \anchor binselection
     *
     * \defgroup binselection BinSelection
     * \ingroup devtools
     * \brief The biniry/group run time selection toolkit.
     * \details 
     * - This toolkit allows to choose the binary functions to be used, at run time.
     * - For every function to be developed by students,
     *   there are 3 versions, using prefixes \b so, \b bin, and \b grp:
     *   - the \b so version is a front end function, which allows to call
     *     the \b bin or \b grp version, based on the binary activation map astate;
     *   - the \b bin version is a binary available version of the function;
     *   - the \b grp version is the one developed by students.
     *   .
     * - This toolkit make available an API to select which binary version to
     *   activate.
     * - Every function has a unique ID
     *   - The ID is the same used by the probbing toolkit (\ref probbing)
     *
     **/

    /** @{ */

    /* *************************************** */

    /**
     *  \brief Set bin configuration map.
     *  \details Resets the current bin configuration and sets the given range
     *    as the new bin configuration map.
     *  \param [in] lower left margin of the range to be activated
     *  \param [in] upper right margin of the range to be activated
     */
    void soBinSetIDs(uint32_t lower, uint32_t upper);

    /* *************************************** */

    /**
     *  \brief Add bin ID range to the current bin configuration map.
     *  \param [in] lower left margin of the range to be added
     *  \param [in] upper right margin of the range to be added
     */
    void soBinAddIDs(uint32_t lower, uint32_t upper);

    /* *************************************** */

    /**
     *  \brief Remove bin ID range from the current bin configuration map.
     *  \param [in] lower left margin of the range to be deactivated
     *  \param [in] upper right margin of the range to be deactivated
     */
    void soBinRemoveIDs(uint32_t lower, uint32_t upper);

    /* *************************************** */

    /**
     *  \brief Check if given ID is activated.
     *  \details IDs covered by the current configuration represent binary functions 
     *    to be used.
     *  \param [in] id ID of the function to be checked
     *  \return \c true if the given ID is included in the current
     *      configuration map and \c false otherwise.
     */
    bool soBinSelected(uint32_t id);

    /* *************************************** */

    /** @} */

};

#endif /* __SOFS20_BIN_SELECTION__ */
