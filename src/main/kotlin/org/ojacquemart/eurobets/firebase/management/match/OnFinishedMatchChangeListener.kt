package org.ojacquemart.eurobets.firebase.management.match

import com.firebase.client.ChildEventListener
import com.firebase.client.DataSnapshot
import com.firebase.client.FirebaseError
import org.ojacquemart.eurobets.firebase.Collections
import org.ojacquemart.eurobets.firebase.config.FirebaseRef
import org.ojacquemart.eurobets.firebase.management.group.GroupsUpdateScheduler
import org.ojacquemart.eurobets.firebase.management.table.TablePersister
import org.ojacquemart.eurobets.firebase.misc.Status
import org.ojacquemart.eurobets.lang.loggerFor
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class OnFinishedMatchChangeListener(val tablePersister: TablePersister, val groupsUpdateScheduler: GroupsUpdateScheduler,
                                    val ref: FirebaseRef) {

    private val log = loggerFor<OnFinishedMatchChangeListener>()

    @PostConstruct
    fun listen() {
        log.info("Listening to finished matches...")

        ref.firebase.child(Collections.matches).addChildEventListener(object : ChildEventListener {

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                p0?.let { ds ->
                    val match = ds.getValue(Match::class.java)
                    if (match.status == Status.PLAYED.id) {
                        log.info("Match #${ds.key} is finished!")

                        persistTable()
                        scheduleGroupsUpdate()
                    }
                }
            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
            }

            override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
            }

            override fun onChildRemoved(p0: DataSnapshot?) {
            }

            override fun onCancelled(p0: FirebaseError?) {
            }
        })

    }

    fun persistTable() {
        log.debug("Persist table")

        tablePersister.persist()
    }

    fun scheduleGroupsUpdate() {
        log.debug("Schedule update groups")

        groupsUpdateScheduler.scheduleUpdate()
    }

}