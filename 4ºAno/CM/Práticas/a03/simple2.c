#include "netlink/netlink.h"
#include "netlink/genl/genl.h"
#include "netlink/genl/ctrl.h"
#include <net/if.h>
#include <signal.h>

//copy this from iw
#include <linux/nl80211.h>

static int expectedId;


//Auxiliary function to catch a segmentation fault
void segfault_sigaction(int signal, siginfo_t *si, void *arg)
{
    printf(">>> Program exit.\n");
    exit(0);
}


static int nlCallback(struct nl_msg* msg, void* arg)
{
    struct nlmsghdr* ret_hdr = nlmsg_hdr(msg); //returns pointer to received message
    struct nlattr *tb_msg[NL80211_ATTR_MAX + 1]; //attributes array

    if (ret_hdr->nlmsg_type != expectedId)
    {
        return NL_STOP;
    }

    //D: Interprets the netlink protocol payload as a generic link header
    struct genlmsghdr *gnlh = (struct genlmsghdr*) nlmsg_data(ret_hdr);

    /*D: Parses the message's attributes,
        Parameters
            tb      Index array to be filled (maxtype+1 elements).
            maxtype	Maximum attribute type expected and accepted.
            head	Head of attribute stream.
            len     Length of attribute stream.
            policy	Attribute validation policy.
    */
    nla_parse(tb_msg, NL80211_ATTR_MAX, genlmsg_attrdata(gnlh, 0), genlmsg_attrlen(gnlh, 0), NULL);
    
    printf("----------------------------------------------------------\n");

    if (tb_msg[NL80211_ATTR_REG_ALPHA2]) 
        printf("NL80211_ATTR_REG_ALPHA2: Region %s\n", nla_get_string(tb_msg[NL80211_ATTR_REG_ALPHA2]));  
    
    
    //First Method
    /*if (tb_msg[NL80211_ATTR_REG_RULES]) {
        struct nlattr * nested[NL80211_MAX_SUPP_REG_RULES+1];
        int err = nla_parse_nested(nested,NL80211_MAX_SUPP_REG_RULES, tb_msg[NL80211_ATTR_REG_RULES],NULL);
        
        struct nlattr * inner[NL80211_MAX_SUPP_REG_RULES+1];
        int err2;

        for(int i=0; i < NL80211_MAX_SUPP_REG_RULES+1; i++){
            err2 = nla_parse_nested(inner,NL80211_MAX_SUPP_REG_RULES, nested[i],NULL);
            printf("ATTR %d\n", i);
            if (inner[NL80211_ATTR_FREQ_RANGE_START]){
                printf("NL80211_ATTR_FREQ_RANGE_START: Range Start:");
                printf(" %d\n",nla_get_u32(inner[NL80211_ATTR_FREQ_RANGE_START]));
            }
            if (inner[NL80211_ATTR_FREQ_RANGE_END]){
                printf("NL80211_ATTR_FREQ_RANGE_END: Range End:");
                printf(" %d\n", nla_get_u32(inner[NL80211_ATTR_FREQ_RANGE_END]));
            }
            if (inner[NL80211_ATTR_FREQ_RANGE_MAX_BW]){
                printf("NL80211_ATTR_FREQ_RANGE_MAX_BW: Range Max Bandwidth:");
                printf(" %d\n", nla_get_u32(inner[NL80211_ATTR_FREQ_RANGE_MAX_BW]));
            }
            if (inner[NL80211_ATTR_POWER_RULE_MAX_ANT_GAIN]){
                printf("NL80211_ATTR_POWER_RULE_MAX_ANT_GAIN: Maximum Allowed Antenna Gain:");
                printf(" %d\n", nla_get_u32(inner[NL80211_ATTR_POWER_RULE_MAX_ANT_GAIN]));
            }
            if (inner[NL80211_ATTR_POWER_RULE_MAX_EIRP]){
                printf("NL80211_ATTR_POWER_RULE_MAX_EIRP: Effective Isotropic Radiated Power:");
                printf(" %d\n", nla_get_u32(inner[NL80211_ATTR_POWER_RULE_MAX_EIRP]));
            }
            if (inner[NL80211_ATTR_DFS_CAC_TIME]){
                printf("NL80211_ATTR_DFS_CAC_TIME: Channel Availability time:");
                printf(" %d\n", nla_get_u32(inner[NL80211_ATTR_DFS_CAC_TIME]));
            }
            printf("----------------------------------------------------------\n");
        }
    }*/


    //Second Method
    struct nlattr * nl_rule;
    int rem_rule;
    
    nla_for_each_nested(nl_rule, tb_msg[NL80211_ATTR_REG_RULES],rem_rule){
        struct nlattr * tb_rule[NL80211_REG_RULE_ATTR_MAX + 1];
        int flags, start_freq_khz, end_freq_khz, max_bw_khz;
        
        nla_parse(tb_rule, NL80211_REG_RULE_ATTR_MAX, nla_data(nl_rule), nla_len(nl_rule), NULL);
        start_freq_khz = nla_get_u32(tb_rule[NL80211_ATTR_FREQ_RANGE_START]);
        printf("freq: %d\n",start_freq_khz);

        if (tb_rule[NL80211_ATTR_FREQ_RANGE_START]){
            printf("NL80211_ATTR_FREQ_RANGE_START: Range Start:");
            printf(" %d\n",nla_get_u32(tb_rule[NL80211_ATTR_FREQ_RANGE_START]));
        }
        if (tb_rule[NL80211_ATTR_FREQ_RANGE_END]){
            printf("NL80211_ATTR_FREQ_RANGE_END: Range End:");
            printf(" %d\n", nla_get_u32(tb_rule[NL80211_ATTR_FREQ_RANGE_END]));
        }
        if (tb_rule[NL80211_ATTR_FREQ_RANGE_MAX_BW]){
            printf("NL80211_ATTR_FREQ_RANGE_MAX_BW: Range Max Bandwidth:");
            printf(" %d\n", nla_get_u32(tb_rule[NL80211_ATTR_FREQ_RANGE_MAX_BW]));
        }
        if (tb_rule[NL80211_ATTR_POWER_RULE_MAX_ANT_GAIN]){
            printf("NL80211_ATTR_POWER_RULE_MAX_ANT_GAIN: Maximum Allowed Antenna Gain:");
            printf(" %d\n", nla_get_u32(tb_rule[NL80211_ATTR_POWER_RULE_MAX_ANT_GAIN]));
        }
        if (tb_rule[NL80211_ATTR_POWER_RULE_MAX_EIRP]){
            printf("NL80211_ATTR_POWER_RULE_MAX_EIRP: Effective Isotropic Radiated Power:");
            printf(" %d\n", nla_get_u32(tb_rule[NL80211_ATTR_POWER_RULE_MAX_EIRP]));
        }
        if (tb_rule[NL80211_ATTR_DFS_CAC_TIME]){
            printf("NL80211_ATTR_DFS_CAC_TIME: Channel Availability time:");
            printf(" %d\n", nla_get_u32(tb_rule[NL80211_ATTR_DFS_CAC_TIME]));
        }
        printf("----------------------------------------------------------\n");
    }

    
}


int main(int argc, char** argv)
{
    //Catch a segmentation fault
    struct sigaction sa;
    memset(&sa, 0, sizeof(struct sigaction));
    sigemptyset(&sa.sa_mask);
    sa.sa_sigaction = segfault_sigaction;
    sa.sa_flags   = SA_SIGINFO;
    sigaction(SIGSEGV, &sa, NULL);


    int ret;
    //allocate socket
    struct nl_sock* sk = nl_socket_alloc();

    //connect to generic netlink socket
    genl_connect(sk);

    //find the nl80211 driver ID
    expectedId = genl_ctrl_resolve(sk, "nl80211");

    //attach a callback
    nl_socket_modify_cb(sk, NL_CB_VALID, NL_CB_CUSTOM,
            nlCallback, NULL);

    //allocate a message
    struct nl_msg* msg = nlmsg_alloc();

    enum nl80211_commands* cmd = NL80211_CMD_GET_REG;
    int ifIndex = if_nametoindex("wlan0");
    int flags = 0;

    // setup the message (adds generic netlink headers)
    genlmsg_put(msg, 0, 0, expectedId, 0, flags, cmd, 0);

    //add message attributes (indicate the operating interface of the device)
    NLA_PUT_U32(msg, NL80211_ATTR_IFINDEX, ifIndex);

    //send the messge (this frees it)
    ret = nl_send_auto_complete(sk, msg);

    //block for message to return
    nl_recvmsgs_default(sk);

    return 0;

    nla_put_failure:
    nlmsg_free(msg);
    return 1;
}
