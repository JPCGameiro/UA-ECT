
/*
 *  This toolkit provides a simple way to select what binary version of
 *  the functions to use in a given moment.
 *  The selection can be done in run time.
 *  The IDs of the functions are the same used by the probing system.
 *
 *  \author Artur Pereira - 2019-2020
 *
 *  \remarks In case an error occurs, every function throws an error code (an int)
 */

#include "binselection.h"

#include <inttypes.h>

namespace sofs20
{
    /* module data structure */
    static bool selected_bin_ids[1000] = { false };
    static bool flag = false;

    /* *************************************** */

    static void soAdjustRange(uint32_t & lower, uint32_t & upper)
    {
        /* adjust range */
        if (lower > upper)
            lower = upper = 0;
        if (lower > 999)
            lower = 999;
        if (upper > 999)
            upper = 999;
    }

    /* *************************************** */

    /*
     *  \brief Set bin IDs.
     *  \details reset current configuration and set given range
     *    as the bin IDs to activate.
     *  \param lower left margin of the range to be activated
     *  \param upper right margin of the range to be activated
     */
    void soBinSetIDs(uint32_t lower, uint32_t upper)
    {
        /* reset configuration */
        for (uint32_t i = 0; i < 1000; i++)
            selected_bin_ids[i] = false;
        flag = true;

        /* adjust given range */
        soAdjustRange(lower, upper);

        /* activate given range */
        for (uint32_t i = lower; i <= upper; i++)
            selected_bin_ids[i] = true;
    }

    /* *************************************** */

    /*
     *  \brief Add bin IDs to the current configuration.
     *  \param lower left margin of the range to be activated
     *  \param upper right margin of the range to be activated
     */
    void soBinAddIDs(uint32_t lower, uint32_t upper)
    {
        /* initialized if not done yet, with an empty range */
        if (flag == false)
            soBinSetIDs(0, 0);

        /* adjust given range */
        soAdjustRange(lower, upper);

        /* activate given range */
        for (uint32_t i = lower; i <= upper; i++)
            selected_bin_ids[i] = true;
    }

    /* *************************************** */

    /*
     *  \brief Remove bin IDs from the current configuration.
     *  \param lower left margin of the range to be deactivated
     *  \param upper right margin of the range to be deactivated
     */
    void soBinRemoveIDs(uint32_t lower, uint32_t upper)
    {
        /* initialized if not done yet, with a full range */
        if (flag == false)
            soBinSetIDs(0, 1000);

        /* adjust given range */
        soAdjustRange(lower, upper);

        /* deactivate given range */
        for (uint32_t i = lower; i <= upper; i++)
            selected_bin_ids[i] = false;
    }

    /* *************************************** */

    /*
     *  \brief Check if given ID is activated.
     *  \details IDs covered by the current configuration represent binary functions 
     *    to be used.
     *    In case of a not valid id return false.
     *  \param id ID of the function to be checked
     *  \return \c true if ID is covered by configuration; \c false otherwise
     */
    bool soBinSelected(uint32_t id)
    {
        /* initialized if not done yet, with an empty range */
        if (flag == false)
            soBinSetIDs(0, 0);

        /* ignore if id not valid */
        if (id >= 1000)
            return false;

        /* return state */
        return selected_bin_ids[id];
    }

    /* *************************************** */

};
