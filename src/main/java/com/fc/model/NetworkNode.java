package com.fc.model;

/**
 * @ClassName: NetworkNode
 * @Description: TODO
 * @Author maChen
 * @Date 2019/1/23 下午8:34
 * @Version V1.0
 **/
import java.util.List;

public class NetworkNode {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_network_node.id
     *
     * @mbggenerated Wed Jan 23 15:44:07 CST 2019
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_network_node.name
     *
     * @mbggenerated Wed Jan 23 15:44:07 CST 2019
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_network_node.parent_id
     *
     * @mbggenerated Wed Jan 23 15:44:07 CST 2019
     */
    private String parentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_network_node.dest
     *
     * @mbggenerated Wed Jan 23 15:44:07 CST 2019
     */
    private String dest;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_network_node.status
     *
     * @mbggenerated Wed Jan 23 15:44:07 CST 2019
     */
    private Integer status; // 1 : 正常  ,  0 : 删除

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_network_node.old_parent_id
     *
     * @mbggenerated Wed Jan 23 15:44:07 CST 2019
     */
    private String oldParentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_network_node.type
     *
     * @mbggenerated Wed Jan 23 15:44:07 CST 2019
     */
    private Integer type;

    private List<NetworkNode> sonNodes;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_network_node.id
     *
     * @return the value of t_network_node.id
     *
     * @mbggenerated Wed Jan 23 15:44:07 CST 2019
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_network_node.id
     *
     * @param id the value for t_network_node.id
     *
     * @mbggenerated Wed Jan 23 15:44:07 CST 2019
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_network_node.name
     *
     * @return the value of t_network_node.name
     *
     * @mbggenerated Wed Jan 23 15:44:07 CST 2019
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_network_node.name
     *
     * @param name the value for t_network_node.name
     *
     * @mbggenerated Wed Jan 23 15:44:07 CST 2019
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_network_node.parent_id
     *
     * @return the value of t_network_node.parent_id
     *
     * @mbggenerated Wed Jan 23 15:44:07 CST 2019
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_network_node.parent_id
     *
     * @param parentId the value for t_network_node.parent_id
     *
     * @mbggenerated Wed Jan 23 15:44:07 CST 2019
     */
    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_network_node.dest
     *
     * @return the value of t_network_node.dest
     *
     * @mbggenerated Wed Jan 23 15:44:07 CST 2019
     */
    public String getDest() {
        return dest;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_network_node.dest
     *
     * @param dest the value for t_network_node.dest
     *
     * @mbggenerated Wed Jan 23 15:44:07 CST 2019
     */
    public void setDest(String dest) {
        this.dest = dest == null ? null : dest.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_network_node.status
     *
     * @return the value of t_network_node.status
     *
     * @mbggenerated Wed Jan 23 15:44:07 CST 2019
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_network_node.status
     *
     * @param status the value for t_network_node.status
     *
     * @mbggenerated Wed Jan 23 15:44:07 CST 2019
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_network_node.old_parent_id
     *
     * @return the value of t_network_node.old_parent_id
     *
     * @mbggenerated Wed Jan 23 15:44:07 CST 2019
     */
    public String getOldParentId() {
        return oldParentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_network_node.old_parent_id
     *
     * @param oldParentId the value for t_network_node.old_parent_id
     *
     * @mbggenerated Wed Jan 23 15:44:07 CST 2019
     */
    public void setOldParentId(String oldParentId) {
        this.oldParentId = oldParentId == null ? null : oldParentId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_network_node.type
     *
     * @return the value of t_network_node.type
     *
     * @mbggenerated Wed Jan 23 15:44:07 CST 2019
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_network_node.type
     *
     * @param type the value for t_network_node.type
     *
     * @mbggenerated Wed Jan 23 15:44:07 CST 2019
     */
    public void setType(Integer type) {
        this.type = type;
    }





    public List<NetworkNode> getSonNodes() {
        return sonNodes;
    }

    public void setSonNodes(List<NetworkNode> sonNodes) {
        this.sonNodes = sonNodes;
    }

    @Override
    public String toString() {
        return "NetworkNode [id=" + id + ", name=" + name + ", parentId=" + parentId + ", dest=" + dest + ", status="
                + status + ", oldParentId=" + oldParentId + ", type=" + type + "]";
    }


}