
#ifndef __SO_IPC_POOL_BUFFERS_
#define __SO_IPC_POOL_BUFFERS_


/** \brief internal storage size of <em>FIFO memory</em> */
#define  POOLSZ         20
#define  DATASZ         200

/*
 *  \brief Data structure.
 */
typedef struct ITEMPOOL
{
    unsigned int id;     ///< id of the producer
    char value[DATASZ];  ///< value stored
} ITEMPOOL;

typedef struct Pool
{ 
    unsigned int ii;   ///< point of insertion
    unsigned int ri;   ///< point of retrieval
    unsigned int cnt;  ///< number of items stored
    ITEMPOOL slot[POOLSZ]; ///< storage memory

    pthread_mutex_t acessCr;        //Mutex to control access to critical zone
} Pool;


/**
 * \brief Init the Pool 
 */
void poolInit(Pool* pool);

/**
 *  \brief Insertion of a value into the Pool.
 *
 * \param id id of the buffer
 * \param value value to be stored
 */
void poolIn(Pool* pool, unsigned int id, char* value);

/**
 *  \brief Retrieval of a value from the Pool.
 *
 * \param id of the buffer of the pool where info will be retrieved
 * \param valuep pointer to recipient where to store the value 
 */
void poolOut (Pool* pool,unsigned int id, char*  *valuep);

#endif /* __SO_IPC_POOL_BUFFERS_ */